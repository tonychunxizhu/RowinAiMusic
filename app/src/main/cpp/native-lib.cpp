#include <jni.h>
#include <string>
#include "curl/curl.h"
#include <iostream>
#include <thread>
#include <chrono>
#include "nlohmann/json.hpp"
#include <android/log.h>
#include <unistd.h>
#include "suno/suno.h"
#include "chatgpt/chatgpt.h"
#include "util/util.h"


using nlohmann::json;
using std::chrono::system_clock;
using std::string;
using namespace std::chrono_literals;

extern "C" JNIEXPORT jstring

JNICALL
Java_com_huawei_tony_rowinaimusic_MainActivity_nativeGetMusicById(
        JNIEnv *env,
        jobject /* this */, jstring _id) {
    string id = jstring_to_string(env, _id);
    LOGD("%s", id.c_str());

    string jsonMusic;
    //string jsonString;
    //jsonString ="[{\"id\":\"af48ad8b-0831-4954-babe-922a868a1237\",\"title\":\"丰收的憧憬\",\"image_url\":\"https://cdn2.suno.ai/image_af48ad8b-0831-4954-babe-922a868a1237.jpeg\",\"lyric\":\"[Verse]\\n夏日的下午 田野里劳作\\n满头大汗 笑容炽热\\n手里的麦子 饱满又饱和\\n丰收的憧憬 心情愉快\\n[Verse 2]\\n回家的路上 一片绿油油\\n四周环绕 鸟儿在歌唱\\n风柔柔吹 田野的芬芳\\n心中满怀 对未来希望\\n[Chorus]\\n哦乡村的美 日复一日\\n辛勤劳作 丰收在望\\n哦生活本味 充满热情\\n在这田野 心情飘扬\\n[Verse 3]\\n家里的饭桌 香气飘散来\\n劳动的果实 美味在盘中\\n一家人团聚 彼此分享\\n这幸福的呢喃 如歌在响\\n[Verse 4]\\n夜幕渐降 月光在闪耀\\n二胡笛子 悠扬的乐声\\n村庄的安宁 在这夜空\\n勤劳的双手 换来这一切\\n[Chorus]\\n哦乡村的美 日复一日\\n辛勤劳作 丰收在望\\n哦生活本味 充满热情\\n在这田野 心情飘扬\",\"audio_url\":\"https://cdn1.suno.ai/af48ad8b-0831-4954-babe-922a868a1237.mp3\",\"video_url\":\"https://cdn1.suno.ai/af48ad8b-0831-4954-babe-922a868a1237.mp4\",\"created_at\":\"2024-08-20T01:05:09.991Z\",\"model_name\":\"chirp-v3.5\",\"status\":\"complete\",\"gpt_description_prompt\":\"夏日的下午，从田野劳作回来，心情愉快，对丰收的前景充满了憧憬，。音乐风格 流行民歌,二胡, 笛子\",\"prompt\":\"[Verse]\\n夏日的下午 田野里劳作\\n满头大汗 笑容炽热\\n手里的麦子 饱满又饱和\\n丰收的憧憬 心情愉快\\n\\n[Verse 2]\\n回家的路上 一片绿油油\\n四周环绕 鸟儿在歌唱\\n风柔柔吹 田野的芬芳\\n心中满怀 对未来希望\\n\\n[Chorus]\\n哦乡村的美 日复一日\\n辛勤劳作 丰收在望\\n哦生活本味 充满热情\\n在这田野 心情飘扬\\n\\n[Verse 3]\\n家里的饭桌 香气飘散来\\n劳动的果实 美味在盘中\\n一家人团聚 彼此分享\\n这幸福的呢喃 如歌在响\\n\\n[Verse 4]\\n夜幕渐降 月光在闪耀\\n二胡笛子 悠扬的乐声\\n村庄的安宁 在这夜空\\n勤劳的双手 换来这一切\\n\\n[Chorus]\\n哦乡村的美 日复一日\\n辛勤劳作 丰收在望\\n哦生活本味 充满热情\\n在这田野 心情飘扬\",\"type\":\"gen\",\"tags\":\"二胡 流行民歌 笛子\",\"duration\":196.88,\"error_message\":null}]";
    long WAIT_TIME = 1500000; //1.5s
    string status;
    for (;;) {
        jsonMusic = get_music_by_id(id);


        //LOGD("AIMUSIC Return by ID %s",jsonMusic.c_str());
//        jsonString = R"([{
//        "id":"af48ad8b-0831-4954-babe-922a868a1237",
//        "title":"",
//        "image_url":null,
//        "lyric":"",
//        "audio_url":"",
//        "video_url":"",
//        "created_at":"2024-08-20T01:05:09.991Z",
//        "model_name":"chirp-v3",
//        "status":"submitted",
//        "gpt_description_prompt":" ",
//        "prompt":"",
//        "type":"gen",
//        "tags":null,
//        "duration":null,
//        "error_message":null
//    }])";

        string video_url;
        string audio_url;
        auto jsonData = json::parse(jsonMusic);

        for (const auto &item: jsonData) {
//        string id = item.at("id").get<std::string>();
//        string title = item.at("title").get<std::string>();
//        string model_name = item.at("model_name").get<std::string>();
            status = item.at("status").get<std::string>();
            video_url = item.at("video_url").get<std::string>();
            LOGD("video %s", video_url.c_str());
            audio_url = item.at("audio_url").get<std::string>();
//        string gpt_description_prompt = item.at("gpt_description_prompt").get<std::string>();
//        string prompt = item.at("prompt").get<std::string>();
//        string type = item.at("type").get<std::string>();
//        // 如果字段值可能是 null 的话需要检查
//        if (item.contains("image_url") && !item["image_url"].is_null()) {
//            std::string image_url = item["image_url"].get<std::string>();
//            std::cout << "Image URL: " << image_url << "\n";
//        } else {
//            std::cout << "Image URL: null\n";
//        }
//
//        if (item.contains("duration") && !item["duration"].is_null()) {
//            double duration = item["duration"].get<double>();
//            std::cout << "Duration: " << duration << "\n";
//        } else {
//            std::cout << "Duration: null\n";
//        }
        }

        if (status != "complete") { //not complete
            LOGD("STATUS: %s", status.c_str());
            usleep(WAIT_TIME);
            continue;
        } else {
            LOGD("STATUS: %s", status.c_str());
            break;
        }
    }

//    jsonMusic = jsonString;

    return env->NewStringUTF(jsonMusic.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_huawei_tony_rowinaimusic_MainActivity_nativeChat(JNIEnv *env, jobject thiz) {

    string prompt = "核心主题是鼓励在困境中坚持自我，寻找希望，并积极行动去改变现状。歌词描绘了一个陷入绝望、看不到未来的情境，但同时也强调了即使身处黑暗，我们仍可以成为自己的光，并通过坚持不懈的努力，最终照亮前方的路.音乐风格Heavy metal, hard rock, guitar, bass, drum, powerful, aggresive";

    string response = get_limit();

    response = generate_music(prompt);
    LOGD("%s", response.c_str());


    return env->NewStringUTF(response.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_huawei_tony_rowinaimusic_MainActivity_nativeGenMusic(JNIEnv *env, jobject thiz,
                                                              jstring prompt) {

    string response;

    response = generate_music(jstring_to_string(env, prompt));
    return env->NewStringUTF(response.c_str());

}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_huawei_tony_rowinaimusic_MainActivity_nativeGetCredit(JNIEnv *env, jobject thiz) {
    return env->NewStringUTF(get_limit().c_str());
}