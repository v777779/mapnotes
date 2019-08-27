package ru.vpcb.map.notes.robots;

import ru.vpcb.map.notes.R;

public class HomeAddNoteRobot extends BaseTestRobot{

    static HomeAddNoteRobot addNoteFragment(){
        return new HomeAddNoteRobot();
    }

    private HomeAddNoteRobot() {
    }

    public HomeAddNoteRobot enterNoteText(String text) {
        enterText(R.id.note,text);
        return this;
    }

    public void pressAddButton() {
        clickOnView(R.id.add);
    }

    public void addNote(String text) {
        enterText(R.id.note,text);
        clickOnView(R.id.add);
    }

    public void isNoteHintDisplayed (int textId){
        isViewHintDisplayed(R.id.note,textId);
    }


    public void isAddButtonEnabled (){
        isViewEnabled(R.id.add);
    }

    public HomeAddNoteRobot isAddButtonDisabled (){
        isViewDisabled(R.id.add);
        return this;
    }

    public void isNoteTextDisplayed (String text){
         isViewWithTextDisplayed(R.id.note,text);
    }

    public void isSuccessfullyDisplayedAddNote (){
        isViewHintDisplayed(R.id.note, R.string.add_note_hint);
         isViewDisabled(R.id.add);
    }


}
