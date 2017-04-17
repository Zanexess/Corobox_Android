package me.labs.corobox.corobox.presenter.main_screen.terms_of_use;

import me.labs.corobox.corobox.common.BaseFragmentPresenter;
import me.labs.corobox.corobox.view.main_screen.categories_fragment.ICategoryFragmentView;
import me.labs.corobox.corobox.view.main_screen.terms_of_use_screen.ITermsFragmentView;

public interface ITermsFragmentPresenter extends BaseFragmentPresenter<ITermsFragmentView> {
    void openTerms();
}
