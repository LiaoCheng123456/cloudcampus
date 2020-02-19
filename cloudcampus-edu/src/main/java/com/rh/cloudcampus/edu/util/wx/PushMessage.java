package com.rh.cloudcampus.edu.util.wx;

public class PushMessage {

    /**
     * 图文消息
     * {
     *    "touser":[
     *     "OPENID1",
     *     "OPENID2"
     *    ],
     *    "mpnews":{
     *       "media_id":"123dsdajkasd231jhksad"
     *    },
     *     "msgtype":"mpnews"，
     *     "send_ignore_reprint":0
     * }
     *
     * @param
     * @return
     */
    public static String getNewsMesage(String touser , String mediaId) {
        String newsMessage = "{\n" +
                "   \"touser\":"+touser+",\n" +
                "   \"mpnews\":{\n" +
                "      \"media_id\":\""+mediaId+"\"\n" +
                "   },\n" +
                "    \"msgtype\":\"mpnews\"，\n" +
                "    \"send_ignore_reprint\":0\n" +
                "}";
        newsMessage = hadleMesage(newsMessage);
        return newsMessage;
    }

    /**
     * 文本
     * {
     *    "touser":[
     *     "OPENID1",
     *     "OPENID2"
     *    ],
     *     "msgtype": "text",
     *     "text": { "content": "hello from boxer."}
     * }
     * @param touser
     * @param
     * @return
     */
    public static String getTextMesage(String touser , String text) {
        String textMessage = "{\n" +
                "   \"touser\":"+touser+",\n" +
                "    \"msgtype\": \"text\",\n" +
                "    \"text\": { \"content\": \""+text+"\"}\n" +
                "}";
        textMessage = hadleMesage(textMessage);
        return textMessage;
    }


    /**
     * 语音
     * {
     *    "touser":[
     *     "OPENID1",
     *     "OPENID2"
     *    ],
     *    "voice":{
     *       "media_id":"mLxl6paC7z2Tl-NJT64yzJve8T9c8u9K2x-Ai6Ujd4lIH9IBuF6-2r66mamn_gIT"
     *    },
     *     "msgtype":"voice"
     * }
     * @param touser
     * @param
     * @return
     */
    public static String getVoiceMesage(String touser , String mediaId) {
        String voiceMessage = "{\n" +
                "   \"touser\":"+touser+",\n" +
                "   \"voice\":{\n" +
                "      \"media_id\":"+mediaId+"\n" +
                "   },\n" +
                "    \"msgtype\":\"voice\"\n" +
                "}";
        voiceMessage = hadleMesage(voiceMessage);
        return voiceMessage;
    }

    public  static String hadleMesage(String message) {
        message= message.replace("\\", "");
        message= message.replace("\"{", "{");
        message= message.replace("}\"", "}");
        return message;
    }
}
