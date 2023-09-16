package edu.uw.tcss450.chatphile.ui.chat;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Edwin
 * @version 4 May 2023
 * This class is used to create mock Chats.
 */

public class ChatListGenerator {
    private static final ArrayList<ChatPreview> CHATS;
    private static final int COUNT = 7;
    private static final String[] mockNames = {"Edwin", "Tony", "Devin", "Jacky", "Uladzimir", "Yihan", "Charles"};
    private static final String[] mockMessage = {"Don't read this", "How exciting!!", "Sprint 2 here we come", "Hopefully I get heroku to work tonight", "We should make the fragments prettier", "Hola amigos", "Mt. Rainer, 10am tomorrow"};
    private static final String[] mockTime = {"3:25 am", "12:00 pm", "10:56 pm", "8:30 pm", "Yesterday", "Sunday", "4/1"};
    private static final int[] mockId = {0, 1, 2, 3, 4, 5, 6};

    static {
        CHATS = new ArrayList<>();

        for (int i = 0; i < COUNT; i++) {
            CHATS.add(new ChatPreview
                    .Builder(mockNames[i], mockMessage[i], mockTime[i], mockId[i])
                    .build());
        }
    }

    /**
     * Getter method to obtain the ArrayList of ChatPreview objects.
     * @return ArrayList of ChatPreview objects
     */
    public static List<ChatPreview> getChatList() {
        return CHATS;
    }

    /**
     * Method that creates a ChatPreview object in order to be added to the
     * already existing ArrayList of ChatPreview objects.
     * @return a ChatPreview object
     */
    public static ChatPreview addChat() {
        return new ChatPreview
                .Builder(mockNames[1], mockMessage[1], mockTime[1], mockId[1])
                .build();
    }

    /**
     * Empty private constructor.
     */
    private ChatListGenerator() {}
}