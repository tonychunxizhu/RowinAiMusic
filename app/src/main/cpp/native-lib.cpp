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
Java_com_huawei_tony_rowinaimusic_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";

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