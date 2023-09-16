package edu.uw.tcss450.chatphile.ui.chat;

import java.io.Serializable;

/**
 * Preview of chat room
 * @author Edwin
 */
public class ChatPreview implements Serializable {

    private final String mContact;
    private final String mPreviewMsg;
    private final String mTimeOfMsg;
    private final int mRoomId;

    /**
     * Helper class for building Credentials.
     *
     * @author Charles Bryan
     */
    public static class Builder {
        private final String mContact;
        private final String mPreviewMsg;
        private final String mTimeOfMsg;
        private final int mRoomId;

        public Builder(String contact, String message, String time, int roomId) {
            this.mContact = contact;
            this.mPreviewMsg = message;
            this.mTimeOfMsg = time;
            this.mRoomId = roomId;
        }

        public ChatPreview build() {

            return new ChatPreview(this);
        }
    }

    /**
     * Constructor
     *
     * @param builder
     */
    private ChatPreview(final Builder builder) {
        this.mContact = builder.mContact;
        this.mPreviewMsg = builder.mPreviewMsg;
        this.mTimeOfMsg = builder.mTimeOfMsg;
        this.mRoomId = builder.mRoomId;
    }

    public String getContact() {
        return mContact;
    }

    public String getPreviewMsg() {
        return mPreviewMsg;
    }

    public String getTimeOfMsg() {
        return mTimeOfMsg;
    }

    public int getRoomId() {
        return mRoomId;
    }
}