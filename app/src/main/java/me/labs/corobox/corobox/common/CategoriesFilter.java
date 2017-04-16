package me.labs.corobox.corobox.common;

import android.util.Log;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import me.labs.corobox.corobox.common.adapters.CategoriesAdapter;
import me.labs.corobox.corobox.model.Category;

public class CategoriesFilter extends Filter {

    private final CategoriesAdapter adapter;

    private final List<Category> originalList;

    private final List<Category> filteredList;

    public CategoriesFilter(CategoriesAdapter adapter, List<Category> originalList) {
        super();
        this.adapter = adapter;
        this.originalList = new LinkedList<>(originalList);
        this.filteredList = new ArrayList<>();
    }
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        filteredList.clear();
        final FilterResults results = new FilterResults();

        if (constraint.length() == 0) {
            filteredList.addAll(originalList);
        } else {
            final String filterPattern = constraint.toString().toLowerCase().trim();

            for (final Category category : originalList) {
                if (category.getTitle().toLowerCase().contains(filterPattern)) {
                    filteredList.add(category);
                }
            }
        }
        results.values = filteredList;
        results.count = filteredList.size();
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
//        adapter.getFilteredCategoriesList().clear();
//        adapter.getFilteredCategoriesList().addAll((ArrayList<Category>) results.values);
//        adapter.notifyDataSetChanged();
    }
}
