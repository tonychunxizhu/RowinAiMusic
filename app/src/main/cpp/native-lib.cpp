#include <jni.h>
#include <string>
#include "curl/curl.h"
#include <iostream>
#include "nlohmann/json.hpp"
#include <android/log.h>
#include <unistd.h>
#include "suno/suno.h"
#include "chatgpt/chatgpt.h"
#include "util/util.h"


using nlohmann::json;
using std::chrono::system_clock;
using std::string;
using std::cout;
using std::endl;
//size_t WriteCallback(void *, size_t, size_t, std::string *);
//size_t WriteCallback(void *, size_t, size_t, void *);

int test();

extern "C" JNIEXPORT jstring

JNICALL
Java_com_huawei_tony_rowinaimusic_MainActivity_nativeGetMusicById(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "[{\"id\":\"3010606e-84e4-4ae0-9a5d-2ed0b8a1ca51\",\"title\":\"自我黎明\",\"image_url\":\"https://cdn2.suno.ai/image_3010606e-84e4-4ae0-9a5d-2ed0b8a1ca51.jpeg\",\"lyric\":\"[Verse]\\n陷于深渊 找不到归途\\n黑夜漫长 希望破灭\\n孤独中挣扎 声音呐喊\\n困境重重 心在燃烧\\n[Verse 2]\\n绝望缠绕 无尽漩涡\\n前方迷茫 无光引导\\n坚持信念 惊雷刺破\\n寒冰厚地 也会融化\\n[Chorus]\\n成为自己的光 照亮未来\\n逆风而行 勇者无惧\\n脚下荆棘 坚韧不拔\\n凭借信仰 终会胜利\\n[Verse 3]\\n每一步 痛苦加剧\\n每一瞬 挫折逼近\\n不曾屈服 心中怒火\\n力量觉醒 再次崛起\\n[Verse 4]\\n风雨交加 灵魂颤抖\\n命运挑战 意志不屈\\n黑夜终结 曙光初现\\n黎明的光 芒耀四方\\n[Chorus]\\n成为自己的光 照亮未来\\n逆风而行 勇者无惧\\n脚下荆棘 坚韧不拔\\n凭借信仰 终会胜利\",\"audio_url\":\"https://cdn1.suno.ai/3010606e-84e4-4ae0-9a5d-2ed0b8a1ca51.mp3\",\"video_url\":\"https://cdn1.suno.ai/3010606e-84e4-4ae0-9a5d-2ed0b8a1ca51.mp4\",\"created_at\":\"2024-08-18T19:20:22.079Z\",\"model_name\":\"chirp-v3.5\",\"status\":\"complete\",\"gpt_description_prompt\":\"核心主题是鼓励在困境中坚持自我，寻找希望，并积极行动去改变现状。歌词描绘了一个陷入绝望、看不到未来的情境，但同时也强调了即使身处黑暗，我们仍可以成为自己的光，并通过坚持不懈的努力，最终照亮前方的路.音乐风格Heavy metal, hard rock, guitar, bass, drum, powerful, aggresive\",\"prompt\":\"[Verse]\\n陷于深渊 找不到归途\\n黑夜漫长 希望破灭\\n孤独中挣扎 声音呐喊\\n困境重重 心在燃烧\\n\\n[Verse 2]\\n绝望缠绕 无尽漩涡\\n前方迷茫 无光引导\\n坚持信念 惊雷刺破\\n寒冰厚地 也会融化\\n\\n[Chorus]\\n成为自己的光 照亮未来\\n逆风而行 勇者无惧\\n脚下荆棘 坚韧不拔\\n凭借信仰 终会胜利\\n\\n[Verse 3]\\n每一步 痛苦加剧\\n每一瞬 挫折逼近\\n不曾屈服 心中怒火\\n力量觉醒 再次崛起\\n\\n[Verse 4]\\n风雨交加 灵魂颤抖\\n命运挑战 意志不屈\\n黑夜终结 曙光初现\\n黎明的光 芒耀四方\\n\\n[Chorus]\\n成为自己的光 照亮未来\\n逆风而行 勇者无惧\\n脚下荆棘 坚韧不拔\\n凭借信仰 终会胜利\",\"type\":\"gen\",\"tags\":\"hard rock drum heavy metal guitar powerful aggressive bass\",\"duration\":167,\"error_message\":null}]";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_huawei_tony_rowinaimusic_MainActivity_nativeChat(JNIEnv *env, jobject thiz) {

    string prompt = "核心主题是鼓励在困境中坚持自我，寻找希望，并积极行动去改变现状。歌词描绘了一个陷入绝望、看不到未来的情境，但同时也强调了即使身处黑暗，我们仍可以成为自己的光，并通过坚持不懈的努力，最终照亮前方的路.音乐风格Heavy metal, hard rock, guitar, bass, drum, powerful, aggresive";

    string response = get_limit();
    //response = get_musics();
    //response = get_music_by_id("829f7413-9b82-4e99-ae2e-33215763bcf1");

    response = generate_music(prompt);
    LOGD("%s", response.c_str());


    return env->NewStringUTF(response.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_huawei_tony_rowinaimusic_MainActivity_nativeGenMusic(JNIEnv *env, jobject thiz,
                                                          jstring prompt) {

    string response;// = "核心主题是鼓励在困境中坚持自我，寻找希望，并积极行动去改变现状。歌词描绘了一个陷入绝望、看不到未来的情境，但同时也强调了即使身处黑暗，我们仍可以成为自己的光，并通过坚持不懈的努力，最终照亮前方的路.音乐风格Heavy metal, hard rock, guitar, bass, drum, powerful, aggresive";

    response = generate_music(jstring_to_string(env, prompt));
    return env->NewStringUTF(response.c_str());

}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_huawei_tony_rowinaimusic_MainActivity_nativeGetCredit(JNIEnv *env, jobject thiz) {
    return env->NewStringUTF(get_limit().c_str());
}