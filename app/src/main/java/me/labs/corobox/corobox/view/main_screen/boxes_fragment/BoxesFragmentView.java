package me.labs.corobox.corobox.view.main_screen.boxes_fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.common.BaseFragment;
import me.labs.corobox.corobox.common.adapters.BoxesAdapter;
import me.labs.corobox.corobox.di.components.activities.IMainActivityComponent;
import me.labs.corobox.corobox.model.realm.Box;
import me.labs.corobox.corobox.presenter.main_screen.boxes_fragment.IBoxesFragmentPresenter;
import me.labs.corobox.corobox.view.main_screen.make_order_screen.MakeOrderActivityView;

import static android.content.Context.MODE_PRIVATE;

public class BoxesFragmentView extends BaseFragment implements IBoxesFragmentView {

    @Inject
    IBoxesFragmentPresenter presenter;

    private View view;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.button_ok) Button returnButton;
    @BindView(R.id.no_stuff) TextView noData;

    private HashSet<String> selected = new HashSet<>();
    private BoxesAdapter boxesAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_boxes, container, false);
            ButterKnife.bind(this, view);
            initComponents();
        }
        return view;
    }

    private void initComponents() {
        LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(llm);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MakeOrderActivityView.class);
                Set<String> set = new HashSet<String>();
                set.addAll(selected);
                selected.clear();

                v.getContext().getSharedPreferences("order_info", MODE_PRIVATE)
                        .edit()
                        .putString("type", "FROM")
                        .putStringSet("set", set)
                        .apply();

                if (boxesAdapter != null)
                    boxesAdapter.notifyDataSetChanged();
                presenter.readyForOrder(selected);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(IMainActivityComponent.class).inject(this);
        presenter.init(this);
        presenter.fetchData();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    public Activity provideActivity() {
        return getActivity();
    }

    @Override
    public void setReadyButtonVisibility(int visible) {
        returnButton.setVisibility(visible);
    }

    @Override
    public void showData(List<Box> boxes) {
        recyclerView.setVisibility(View.VISIBLE);
        noData.setVisibility(View.GONE);
        boxesAdapter = new BoxesAdapter(boxes, presenter, selected);
        recyclerView.setAdapter(boxesAdapter);
    }

    @Override
    public void showEmptyData() {
        recyclerView.setVisibility(View.GONE);
        noData.setVisibility(View.VISIBLE);
    }
}
