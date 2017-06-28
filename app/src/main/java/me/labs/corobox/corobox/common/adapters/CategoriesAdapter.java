package me.labs.corobox.corobox.common.adapters;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.common.CategoriesFilter;
import me.labs.corobox.corobox.model.realm.Category;
import me.labs.corobox.corobox.presenter.main_screen.IMainActivityPresenter;
import me.labs.corobox.corobox.presenter.main_screen.categories_fragment.ICategoryFragmentPresenter;

public class CategoriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private List<Category> categories;
    private List<Category> categoriesFiltered;
    private HashMap<String, Integer> hashMap;
    private ICategoryFragmentPresenter presenter;
    private IMainActivityPresenter presenterActivity;
    private CategoriesFilter categoriesFilter;
    private RecyclerView recyclerView;

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    public CategoriesAdapter(List<Category> categories, HashMap<String, Integer> hashMap, ICategoryFragmentPresenter presenter, IMainActivityPresenter presenterActivity) {
        this.categories = categories;
        this.categoriesFiltered = new ArrayList<>();
        this.presenter = presenter;
        this.presenterActivity = presenterActivity;
        this.hashMap = hashMap;
    }

    public List<Category> getCategoriesFiltered() {
        return categoriesFiltered;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new CategoryHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CategoryHolder categoryHolder = ((CategoryHolder)holder);
        categoryHolder.bindModel(position);
    }

    @Override
    public int getItemCount() {
        return categoriesFiltered.size() != 0 ? categoriesFiltered.size() : categories.size();
    }

    @Override
    public Filter getFilter() {
        if (categoriesFilter == null) {
            categoriesFilter = new CategoriesFilter(this, categories);
        }
        return categoriesFilter;
    }

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
                    List<Category> categories = categoriesFiltered.size() == 0 ? CategoriesAdapter.this.categories : categoriesFiltered;
                    String key = categories.get(getAdapterPosition()).getCategory_id();
                    Integer value = hashMap.get(key);

                    if (value == 0) {
                        hashMap.put(key, 1);
                        presenterActivity.updateBadgeCounter(1);
                        price.setText(String.valueOf(categories.get(getAdapterPosition()).getPrice()));
                        constraintLayout.setVisibility(View.VISIBLE);
                        number.setText("1 шт.");
                    }

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.getLayoutManager().scrollToPosition(getAdapterPosition());
                        }
                    }, 30);
                }
            });

            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<Category> categories = categoriesFiltered.size() == 0 ? CategoriesAdapter.this.categories : categoriesFiltered;
                    String key = categories.get(getAdapterPosition()).getCategory_id();
                    Integer value = hashMap.get(key);
                    hashMap.put(key, value + 1);
                    presenterActivity.updateBadgeCounter(1);
                    price.setText(String.valueOf(categories.get(getAdapterPosition()).getPrice() * (value + 1)));
                    number.setText((value + 1) + " шт.");

                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<Category> categories = categoriesFiltered.size() == 0 ? CategoriesAdapter.this.categories : categoriesFiltered;
                    String key = categories.get(getAdapterPosition()).getCategory_id();
                    Integer value = hashMap.get(key);
                    hashMap.put(key, value - 1);
                    presenterActivity.updateBadgeCounter(-1);
                    price.setText(String.valueOf(categories.get(getAdapterPosition()).getPrice() * (value - 1)));
                    number.setText((value - 1) + " шт.");

                    if (value - 1 == 0)
                        constraintLayout.setVisibility(View.GONE);
                }
            });
        }

        private void bindModel(int position) {
            List<Category> categories = categoriesFiltered.size() == 0 ?
                    CategoriesAdapter.this.categories :
                    categoriesFiltered;

            String key = categories.get(position).getCategory_id();
            Integer value = hashMap.get(key);

            price.setText(String.valueOf(categories.get(position).getPrice() * value));
            number.setText((value) + " шт.");
            title.setText(categories.get(position).getTitle());

            Picasso.with(imageView.getContext())
                    .load(categories.get(position).getPicture())
                    .error(R.drawable.error_placeholder)
                    .into(imageView);

            if (value == 0)
                constraintLayout.setVisibility(View.GONE);
            else
                constraintLayout.setVisibility(View.VISIBLE);
        }
    }

    public void clearAll() {
        categoriesFiltered.clear();
//        categories.clear();
        hashMap.clear();
    }
}
