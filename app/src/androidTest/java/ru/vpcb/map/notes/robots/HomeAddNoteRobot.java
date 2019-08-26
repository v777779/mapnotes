package ru.vpcb.map.notes.robots;

import androidx.test.espresso.ViewInteraction;

import ru.vpcb.map.notes.R;

public class HomeAddNoteRobot extends BaseTestRobot{

    static HomeAddNoteRobot addNoteFragment(){
        return new HomeAddNoteRobot();
    }

    private HomeAddNoteRobot() {
    }

    public void enterNoteText(String text) {
        enterText(R.id.note,text);
    }

    public void pressAddButton() {
        clickOnView(R.id.add);
    }

    public void addNote(String text) {
        enterText(R.id.note,text);
        clickOnView(R.id.add);
    }

    public ViewInteraction isNoteHintDisplayed (int textId){
        return isViewHintDisplayed(R.id.note,textId);
    }


    public ViewInteraction isAddButtonEnabled (){
        return isViewEnabled(R.id.add);
    }

    public ViewInteraction isAddButtonDisabled (){
        return isViewDisabled(R.id.add);
    }

    public ViewInteraction isNoteTextDisplayed (String text){
        return isViewWithTextDisplayed(R.id.note,text);
    }

    public ViewInteraction isSuccessfullyDisplayedAddNote (){
        isViewHintDisplayed(R.id.note, R.string.add_note_hint);
        return isViewDisabled(R.id.add);
    }


}
