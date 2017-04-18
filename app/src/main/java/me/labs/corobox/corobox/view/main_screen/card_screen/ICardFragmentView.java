package me.labs.corobox.corobox.view.main_screen.card_screen;

import android.app.Activity;

public interface ICardFragmentView {
    Activity provideActivity();
    void deleteCard(String uuid);
}
