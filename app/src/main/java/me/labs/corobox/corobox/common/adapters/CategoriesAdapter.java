package me.labs.corobox.corobox.common.adapters;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.common.CategoriesFilter;
import me.labs.corobox.corobox.model.Category;
import me.labs.corobox.corobox.presenter.main_screen.IMainActivityPresenter;
import me.labs.corobox.corobox.presenter.main_screen.categories_fragment.ICategoryFragmentPresenter;

public class CategoriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Category> categories;
    private SparseIntArray hashMap;
    private ICategoryFragmentPresenter presenter;
    private IMainActivityPresenter presenterActivity;
    private CategoriesFilter categoriesFilter;

    public CategoriesAdapter(ArrayList<Category> categories, ICategoryFragmentPresenter presenter, IMainActivityPresenter presenterActivity) {
        this.categories = categories;
        this.presenter = presenter;
        this.presenterActivity = presenterActivity;
        this.hashMap = new SparseIntArray();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new CategoryHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CategoryHolder categoryHolder = ((CategoryHolder)holder);
        categoryHolder.title.setText(categories.get(position).getTitle());
        categoryHolder.imageView.setImageResource(categories.get(position).getPicture());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

//    @Override
//    public Filter getFilter() {
//        if (categoriesFilter == null) {
//            categoriesFilter = new CategoriesFilter(this, categories);
//        }
//        return categoriesFilter;
//    }

    private class CategoryHolder extends RecyclerView.ViewHolder {

        ImageView deleteButton;
        ImageView addButton;
        ImageView imageView;
        TextView price;
        TextView title;
        TextView number;
        ConstraintLayout constraintLayout;

        public CategoryHolder(View itemView) {
            super(itemView);
            deleteButton = (ImageView) itemView.findViewById(R.id.deleteObject);
            addButton = (ImageView) itemView.findViewById(R.id.addObject);
            number = (TextView) itemView.findViewById(R.id.number);
            title = (TextView) itemView.findViewById(R.id.title_category);
            price = (TextView) itemView.findViewById(R.id.price);

            imageView = (ImageView) itemView.findViewById(R.id.image_category);

            constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.constraint_layout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (hashMap.get(getAdapterPosition()) == 0) {
                        hashMap.put(getAdapterPosition(), 1);
                        presenterActivity.updateBadgeCounter(1);
                        constraintLayout.setVisibility(View.VISIBLE);
                        number.setText(1 + " шт.");
                        price.setText("" + categories.get(getAdapterPosition()).getPrice());
                    }
                }
            });

            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int numberInt = hashMap.get(getAdapterPosition());
                    hashMap.put(getAdapterPosition(), numberInt + 1);
                    presenterActivity.updateBadgeCounter(1);
                    number.setText((numberInt + 1) + " шт.");
                    price.setText("" + (numberInt + 1) * categories.get(getAdapterPosition()).getPrice());
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int numberInt = hashMap.get(getAdapterPosition());
                    hashMap.put(getAdapterPosition(), numberInt - 1);
                    presenterActivity.updateBadgeCounter(-1);
                    number.setText((numberInt - 1) + " шт.");
                    price.setText("" + (numberInt - 1) * categories.get(getAdapterPosition()).getPrice());
                    if (numberInt - 1 == 0) {
                        constraintLayout.setVisibility(View.GONE);
                    }
                }
            });
        }
    }
}
