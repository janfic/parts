package com.janfic.games.dddserver.epochsapart.cards;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.janfic.games.dddserver.epochsapart.cards.actioncards.InspectActionCard;

import java.util.ArrayList;
import java.util.List;

public abstract class Card extends Table implements Json.Serializable {
    protected String name;
    //protected int id;
    private TextureRegion face, back;
    private Image imageFace, imageBack;
    private boolean isFaceUp;

    protected static List<TextureRegion> playingCards;

    protected Table informationTable;

    public Card() {
        setOrigin(Align.center);
        if(playingCards == null) {
            List<TextureRegion> frames = new ArrayList<>();
            Texture tex = new Texture("cards/playing_cards.png");
            int mw = 8;
            int mh = 7;
            int w = tex.getWidth() / mw;
            int h = tex.getHeight() / mh;
            for (int y = 0; y < mh; y++) {
                for (int x = 0; x < mw; x++) {
                    frames.add(new TextureRegion(tex, x * w, y * h, w, h));
                }
            }
            playingCards = frames;
        }
    }

    public Card(String name) {
        this();
        this.name = name;
    }

    public void setBack(TextureRegion back) {
        this.back = back;
        imageBack = new Image(back);
        imageBack.setSize(62,83);
    }

    public void setFace(TextureRegion face) {
        this.face = face;
        imageFace = new Image(face);
        imageFace.setSize(62,83);
    }

    public TextureRegion getFace() {
        return face;
    }

    public TextureRegion getBack() {
        return back;
    }

    public void setInformationTable(Table informationTable) {
        this.informationTable = informationTable;
    }

    public String getName() {
        return name;
    }

    @Override
    public void write(Json json) {
        json.writeValue("name", name);
        json.setTypeName("class");
        json.writeType(getClass());
        json.setTypeName(null);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        name = json.readValue("name", String.class, jsonData);
    }

    public void setFaceUp(boolean faceUp) {
        isFaceUp = faceUp;
        clear();
        add(isFaceUp ? imageFace : imageBack).top().row();
    }

    public Table getInformationTable() {
        return informationTable;
    }

    @Override
    public void setColor(Color color) {
        super.setColor(color);
        for (Actor child : getChildren()) {
            child.setColor(color);
        }
    }
}
