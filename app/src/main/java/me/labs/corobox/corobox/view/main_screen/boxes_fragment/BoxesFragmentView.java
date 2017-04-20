package me.labs.corobox.corobox.view.main_screen.boxes_fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import javax.inject.Inject;

import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.common.BaseFragment;
import me.labs.corobox.corobox.common.adapters.BoxesAdapter;
import me.labs.corobox.corobox.di.components.activities.IMainActivityComponent;
import me.labs.corobox.corobox.model.realm.Box;
import me.labs.corobox.corobox.presenter.main_screen.boxes_fragment.IBoxesFragmentPresenter;

public class BoxesFragmentView extends BaseFragment implements IBoxesFragmentView {

    @Inject
    IBoxesFragmentPresenter presenter;

    private View view;
    private RecyclerView recyclerView;
    private Button readyButton;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_boxes, container, false);
            initComponents();
        }
        return view;
    }

    private void initComponents() {
        readyButton = (Button) view.findViewById(R.id.button_ok);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(llm);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(IMainActivityComponent.class).inject(this);
        presenter.init(this);

        ArrayList<Box> boxes = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            boxes.add(new Box());
        }
        recyclerView.setAdapter(new BoxesAdapter(boxes, presenter));
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
        readyButton.setVisibility(visible);
    }
}
