package com.huawei.tony.rowinaimusic.jsondata;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huawei.tony.rowinaimusic.Musics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonData
{

    int creditLeft=0;
    //String []idFromGen;
//    List<String> idFromGen = new ArrayList<>();
//    List<String> statusFromGen = new ArrayList<>();

    List<ReturnItem> returnItems= new ArrayList<>();
    List<MusicItem>  musicItems = new ArrayList<>();
    String videoUrl="";
    String audioUrl="";

    public int getCredit(String jsonString){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonUsage usageData = objectMapper.readValue(jsonString, JsonUsage.class);

            // 现在你可以访问usageData对象中的数据
            creditLeft = usageData.getCreditsLeft();
            System.out.println("Credits Left: " + usageData.getCreditsLeft());
            System.out.println("Period: " + usageData.getPeriod());
            System.out.println("Monthly Limit: " + usageData.getMonthlyLimit());
            System.out.println("Monthly Usage: " + usageData.getMonthlyUsage());
        } catch (Exception e) {
            System.out.printf(e.toString());
            e.printStackTrace();
        }
        return  creditLeft;
    }

    public String getVideoUrlByID(String jsonString) {

        jsonString = "[{ \"id\": \"b117b4d2-5a16-44ad-aa3f-abf6b448ee63\", \"title\": \"自我黎明\", \"image_url\": \"https://cdn2.suno.ai/image_b117b4d2-5a16-44ad-aa3f-abf6b448ee63.jpeg\", \"lyric\": \"the lyric\", \"audio_url\": \"https://cdn1.suno.ai/b117b4d2-5a16-44ad-aa3f-abf6b448ee63.mp3\", \"video_url\": \"https://cdn1.suno.ai/b117b4d2-5a16-44ad-aa3f-abf6b448ee63.mp4\", \"created_at\": \"2024-08-18T19:20:22.079Z\", \"model_name\": \"chirp-v3.5\", \"status\": \"complete\", \"gpt_description_prompt\": \"description\", \"prompt\": \"geneated prompt\", \"type\": \"gen\", \"tags\": \"hard rock drum heavy metal guitar powerful aggressive bass\", \"duration\": 150.2, \"error_message\": null }]";

        try {
            // 解析 JSON 字符串
            ObjectMapper objectMapper = new ObjectMapper();
            List<JsonMusicByID> items = objectMapper.readValue(jsonString, new TypeReference<List<JsonMusicByID>>() {});

            // 打印解析后的对象
            for (JsonMusicByID item : items) {
                System.out.println("ID: " + item.getId());
                System.out.println("Title: " + item.getTitle());
                System.out.println("Image URL: " + item.getImageUrl());
                System.out.println("Audio URL: " + item.getAudioUrl());
                System.out.println("Duration: " + item.getDuration());
                System.out.println("Lyric: "+item.getLyric());
                System.out.println("Tags: " + item.getTags());
                // 其他属性...
                System.out.println();

                videoUrl=item.getVideoUrl();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return videoUrl;
    }
    public String getAudioUrlByID(String jsonString) {

        //jsonString = "[{ \"id\": \"b117b4d2-5a16-44ad-aa3f-abf6b448ee63\", \"title\": \"自我黎明\", \"image_url\": \"https://cdn2.suno.ai/image_b117b4d2-5a16-44ad-aa3f-abf6b448ee63.jpeg\", \"lyric\": \"the lyric\", \"audio_url\": \"https://cdn1.suno.ai/b117b4d2-5a16-44ad-aa3f-abf6b448ee63.mp3\", \"video_url\": \"https://cdn1.suno.ai/b117b4d2-5a16-44ad-aa3f-abf6b448ee63.mp4\", \"created_at\": \"2024-08-18T19:20:22.079Z\", \"model_name\": \"chirp-v3.5\", \"status\": \"complete\", \"gpt_description_prompt\": \"description\", \"prompt\": \"geneated prompt\", \"type\": \"gen\", \"tags\": \"hard rock drum heavy metal guitar powerful aggressive bass\", \"duration\": 150.2, \"error_message\": null }]";

        try {
            // 解析 JSON 字符串
            ObjectMapper objectMapper = new ObjectMapper();
            List<JsonMusicByID> items = objectMapper.readValue(jsonString, new TypeReference<List<JsonMusicByID>>() {});

            // 打印解析后的对象
            for (JsonMusicByID item : items) {
                System.out.println("Audio URL: " + item.getAudioUrl());
                audioUrl=item.getAudioUrl();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return audioUrl;
    }

    public String getVideoUrlClipByID(String jsonString){ //jsonString is the return json string when creating a new music
        //jsonString = "{\"id\":\"829f7413-9b82-4e99-ae2e-33215763bcf1\",\"video_url\":\"https://cdn1.suno.ai/829f7413-9b82-4e99-ae2e-33215763bcf1.mp4\",\"audio_url\":\"https://cdn1.suno.ai/829f7413-9b82-4e99-ae2e-33215763bcf1.mp3\",\"image_url\":\"https://cdn2.suno.ai/image_829f7413-9b82-4e99-ae2e-33215763bcf1.jpeg\",\"image_large_url\":\"https://cdn2.suno.ai/image_large_829f7413-9b82-4e99-ae2e-33215763bcf1.jpeg\",\"is_video_pending\":false,\"major_model_version\":\"v3.5\",\"model_name\":\"chirp-v3.5\",\"metadata\":{\"tags\":\"古典乡村 风格 轻快 喜庆\",\"negative_tags\":null,\"prompt\":\"[Verse]\\n鸟儿歌唱 田间花开\\n杨柳青青 风吹麦浪\\n农家小院 笑声飘扬\\n春天到来 欢乐满村\\n\\n[Verse 2]\\n古筝轻弹 和风细语\\n二胡悠扬 抒发心意\\n笛声悠长 随梦远行\\n村庄温馨 乐在其中\\n\\n[Chorus]\\n欢天喜地 采撷希望\\n满园欢笑 春光灿烂\\n田园风光 美好无限\\n大地丰收 欢乐飞扬\\n\\n[Bridge]\\n绿树成荫 流水荡漾\\n河边踏青 儿童嬉戏\\n村头老树 诉说往事\\n乡村梦境 魂牵梦绕\\n\\n[Verse 3]\\n田间劳作 汗水晶莹\\n红火日子 充满生机\\n老少齐聚 欢声笑语\\n春天故事 温暖记忆\\n\\n[Chorus]\\n欢天喜地 采撷希望\\n满园欢笑 春光灿烂\\n田园风光 美好无限\\n大地丰收 欢乐飞扬\",\"gpt_description_prompt\":\"生成一段欢快的中国古典乡村音乐。音乐应融合传统的中国乐器，如古筝、二胡和笛子，展现出乡村的自然风光和节日的喜庆氛围。旋律应轻快、愉悦，能够让人联想到春天的田野、丰收的喜悦和乡村的热闹场景。\",\"audio_prompt_id\":null,\"history\":null,\"concat_history\":null,\"stem_from_id\":null,\"type\":\"gen\",\"duration\":160.96,\"refund_credits\":false,\"stream\":true,\"infill\":null,\"has_vocal\":null,\"is_audio_upload_tos_accepted\":null,\"error_type\":null,\"error_message\":null,\"configurations\":null,\"artist_clip_id\":null,\"cover_clip_id\":null},\"reaction\":null,\"display_name\":\"UnforgivingPolyrhythm132\",\"handle\":\"unforgivingpolyrhythm132\",\"is_handle_updated\":false,\"avatar_image_url\":\"https://cdn1.suno.ai/defaultPink.webp\",\"is_following_creator\":false,\"user_id\":\"a6359ba3-0580-4d2a-9d89-25cae02f6fb9\",\"created_at\":\"2024-08-18T05:21:01.495Z\",\"status\":\"complete\",\"title\":\"欢乐春田\",\"play_count\":4,\"upvote_count\":0,\"is_public\":false}"; // 你的JSON字符串

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonClipByID jsonData = objectMapper.readValue(jsonString, JsonClipByID.class);

            // 现在你可以访问jsonData对象中的数据
            System.out.println("ID: " + jsonData.getId());
            videoUrl = jsonData.getVideoUrl();
            System.out.println("Video URL: " + jsonData.getVideoUrl());
            // 其他字段...
        } catch (Exception e) {
            e.printStackTrace();
        }
        return videoUrl;
    }


    public String getAudioUrlClipByID(String jsonString){ //jsonString is the return json string when creating a new music
        //jsonString = "{\"id\":\"829f7413-9b82-4e99-ae2e-33215763bcf1\",\"video_url\":\"https://cdn1.suno.ai/829f7413-9b82-4e99-ae2e-33215763bcf1.mp4\",\"audio_url\":\"https://cdn1.suno.ai/829f7413-9b82-4e99-ae2e-33215763bcf1.mp3\",\"image_url\":\"https://cdn2.suno.ai/image_829f7413-9b82-4e99-ae2e-33215763bcf1.jpeg\",\"image_large_url\":\"https://cdn2.suno.ai/image_large_829f7413-9b82-4e99-ae2e-33215763bcf1.jpeg\",\"is_video_pending\":false,\"major_model_version\":\"v3.5\",\"model_name\":\"chirp-v3.5\",\"metadata\":{\"tags\":\"古典乡村 风格 轻快 喜庆\",\"negative_tags\":null,\"prompt\":\"[Verse]\\n鸟儿歌唱 田间花开\\n杨柳青青 风吹麦浪\\n农家小院 笑声飘扬\\n春天到来 欢乐满村\\n\\n[Verse 2]\\n古筝轻弹 和风细语\\n二胡悠扬 抒发心意\\n笛声悠长 随梦远行\\n村庄温馨 乐在其中\\n\\n[Chorus]\\n欢天喜地 采撷希望\\n满园欢笑 春光灿烂\\n田园风光 美好无限\\n大地丰收 欢乐飞扬\\n\\n[Bridge]\\n绿树成荫 流水荡漾\\n河边踏青 儿童嬉戏\\n村头老树 诉说往事\\n乡村梦境 魂牵梦绕\\n\\n[Verse 3]\\n田间劳作 汗水晶莹\\n红火日子 充满生机\\n老少齐聚 欢声笑语\\n春天故事 温暖记忆\\n\\n[Chorus]\\n欢天喜地 采撷希望\\n满园欢笑 春光灿烂\\n田园风光 美好无限\\n大地丰收 欢乐飞扬\",\"gpt_description_prompt\":\"生成一段欢快的中国古典乡村音乐。音乐应融合传统的中国乐器，如古筝、二胡和笛子，展现出乡村的自然风光和节日的喜庆氛围。旋律应轻快、愉悦，能够让人联想到春天的田野、丰收的喜悦和乡村的热闹场景。\",\"audio_prompt_id\":null,\"history\":null,\"concat_history\":null,\"stem_from_id\":null,\"type\":\"gen\",\"duration\":160.96,\"refund_credits\":false,\"stream\":true,\"infill\":null,\"has_vocal\":null,\"is_audio_upload_tos_accepted\":null,\"error_type\":null,\"error_message\":null,\"configurations\":null,\"artist_clip_id\":null,\"cover_clip_id\":null},\"reaction\":null,\"display_name\":\"UnforgivingPolyrhythm132\",\"handle\":\"unforgivingpolyrhythm132\",\"is_handle_updated\":false,\"avatar_image_url\":\"https://cdn1.suno.ai/defaultPink.webp\",\"is_following_creator\":false,\"user_id\":\"a6359ba3-0580-4d2a-9d89-25cae02f6fb9\",\"created_at\":\"2024-08-18T05:21:01.495Z\",\"status\":\"complete\",\"title\":\"欢乐春田\",\"play_count\":4,\"upvote_count\":0,\"is_public\":false}"; // 你的JSON字符串

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonClipByID jsonData = objectMapper.readValue(jsonString, JsonClipByID.class);

            // 现在你可以访问jsonData对象中的数据
            System.out.println("ID: " + jsonData.getId());
            audioUrl = jsonData.getAudioUrl();
            System.out.println("Audio URL: " + jsonData.getAudioUrl());
            // 其他字段...
        } catch (Exception e) {
            e.printStackTrace();
        }
        return audioUrl;
    }

    public List<ReturnItem> getIDbyGenRet(String jsonString){
        //jsonString="[{\"id\":\"3010606e-84e4-4ae0-9a5d-2ed0b8a1ca51\",\"title\":\"\",\"image_url\":null,\"lyric\":\"\",\"audio_url\":\"\",\"video_url\":\"\",\"created_at\":\"2024-08-18T19:20:22.079Z\",\"model_name\":\"chirp-v3\",\"status\":\"submitted\",\"gpt_description_prompt\":\"description0\",\"prompt\":\"\",\"type\":\"gen\",\"tags\":null,\"duration\":null},{\"id\":\"b117b4d2-5a16-44ad-aa3f-abf6b448ee63\",\"title\":\"\",\"image_url\":null,\"lyric\":\"\",\"audio_url\":\"\",\"video_url\":\"\",\"created_at\":\"2024-08-18T19:20:22.079Z\",\"model_name\":\"chirp-v3\",\"status\":\"submitted\",\"gpt_description_prompt\":\"description1\",\"prompt\":\"\",\"type\":\"gen\",\"tags\":null,\"duration\":null}]";
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            //字符串
            List<JsonGenerate> items = objectMapper.readValue(jsonString, new TypeReference<List<JsonGenerate>>(){});

            // 解析 JSON 文件
            //List<Item> items = objectMapper.readValue(new File("return.json"), new TypeReference<List<Item>>() {});

            // 打印解析后的对象
            for (JsonGenerate item : items) {
                ReturnItem returnItem =  new ReturnItem();
                returnItem.id = item.getId();
                returnItem.status=item.getStatus();
                returnItems.add(returnItem);
//                idFromGen.add(item.getId());
//                statusFromGen.add(item.getStatus());
                System.out.println(item.getId());
                System.out.println(item.getStatus());
                // 其他属性...
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnItems;
    }

    public List<MusicItem> getMusicItems(String jsonString){
        //jsonString = "[{\"id\":\"3010606e-84e4-4ae0-9a5d-2ed0b8a1ca51\",\"title\":\"自我黎明\",\"image_url\":\"https://cdn2.suno.ai/image_3010606e-84e4-4ae0-9a5d-2ed0b8a1ca51.jpeg\",\"lyric\":\"[Verse]\\n陷于深渊 找不到归途\\n黑夜漫长 希望破灭\\n孤独中挣扎 声音呐喊\\n困境重重 心在燃烧\\n[Verse 2]\\n绝望缠绕 无尽漩涡\\n前方迷茫 无光引导\\n坚持信念 惊雷刺破\\n寒冰厚地 也会融化\\n[Chorus]\\n成为自己的光 照亮未来\\n逆风而行 勇者无惧\\n脚下荆棘 坚韧不拔\\n凭借信仰 终会胜利\\n[Verse 3]\\n每一步 痛苦加剧\\n每一瞬 挫折逼近\\n不曾屈服 心中怒火\\n力量觉醒 再次崛起\\n[Verse 4]\\n风雨交加 灵魂颤抖\\n命运挑战 意志不屈\\n黑夜终结 曙光初现\\n黎明的光 芒耀四方\\n[Chorus]\\n成为自己的光 照亮未来\\n逆风而行 勇者无惧\\n脚下荆棘 坚韧不拔\\n凭借信仰 终会胜利\",\"audio_url\":\"https://cdn1.suno.ai/3010606e-84e4-4ae0-9a5d-2ed0b8a1ca51.mp3\",\"video_url\":\"https://cdn1.suno.ai/3010606e-84e4-4ae0-9a5d-2ed0b8a1ca51.mp4\",\"created_at\":\"2024-08-18T19:20:22.079Z\",\"model_name\":\"chirp-v3.5\",\"status\":\"complete\",\"gpt_description_prompt\":\"核心主题是鼓励在困境中坚持自我，寻找希望，并积极行动去改变现状。歌词描绘了一个陷入绝望、看不到未来的情境，但同时也强调了即使身处黑暗，我们仍可以成为自己的光，并通过坚持不懈的努力，最终照亮前方的路.音乐风格Heavy metal, hard rock, guitar, bass, drum, powerful, aggresive\",\"prompt\":\"[Verse]\\n陷于深渊 找不到归途\\n黑夜漫长 希望破灭\\n孤独中挣扎 声音呐喊\\n困境重重 心在燃烧\\n\\n[Verse 2]\\n绝望缠绕 无尽漩涡\\n前方迷茫 无光引导\\n坚持信念 惊雷刺破\\n寒冰厚地 也会融化\\n\\n[Chorus]\\n成为自己的光 照亮未来\\n逆风而行 勇者无惧\\n脚下荆棘 坚韧不拔\\n凭借信仰 终会胜利\\n\\n[Verse 3]\\n每一步 痛苦加剧\\n每一瞬 挫折逼近\\n不曾屈服 心中怒火\\n力量觉醒 再次崛起\\n\\n[Verse 4]\\n风雨交加 灵魂颤抖\\n命运挑战 意志不屈\\n黑夜终结 曙光初现\\n黎明的光 芒耀四方\\n\\n[Chorus]\\n成为自己的光 照亮未来\\n逆风而行 勇者无惧\\n脚下荆棘 坚韧不拔\\n凭借信仰 终会胜利\",\"type\":\"gen\",\"tags\":\"hard rock drum heavy metal guitar powerful aggressive bass\",\"duration\":167,\"error_message\":null}]";
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            //字符串
            List<JsonMusicByID> items = objectMapper.readValue(jsonString, new TypeReference<List<JsonMusicByID>>(){});

            for (JsonMusicByID item : items) {
                MusicItem musicItem = new MusicItem();
                musicItem.id = item.getId();
                musicItem.title=item.getTitle();
                musicItem.audioUrl=item.getAudioUrl();
                musicItem.videoUrl=item.getVideoUrl();
                musicItem.status=item.getStatus();
                musicItem.duration=item.getDuration();
                musicItem.lyric=item.getLyric();
                musicItem.imageUrl=item.getImageUrl();
                musicItem.status = item.getStatus();
                musicItems.add(musicItem);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return musicItems;
    }


}
