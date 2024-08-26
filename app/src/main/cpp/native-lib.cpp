#include <jni.h>
#include <string>
#include "curl/curl.h"
#include <iostream>
#include <thread>
#include <chrono>
#include <fstream>
#include <filesystem>

#include "nlohmann/json.hpp"
#include <android/log.h>
#include <unistd.h>
#include "suno/suno.h"
#include "chatgpt/chatgpt.h"
#include "util/util.h"
#define  TAG "AIMUSIC-NATIVE-nativelib "
using std::endl;
using nlohmann::json;
using std::chrono::system_clock;
using std::string;
using namespace std::chrono_literals;
using std::ofstream;
using std::ios;


extern "C" JNIEXPORT jstring

JNICALL
Java_com_aiberry_tony_rowinaimusic_ui_genmusic_GenMusicFragment_nativeGetMusicById(
        JNIEnv *env,
        jobject /* this */, jstring _id, jstring _logfile) {
    string id = jstring_to_string(env, _id);
    LOGD("%s", id.c_str());


    ofstream logfile;
    logfile.open(jstring_to_string(env,_logfile), ios::app);

    if(logfile.is_open()){
        LOGD("%s", "Logfile is open");
    } else{
        LOGD("%s", "Logfile is not open");
    }
    LOGD("%s", jstring_to_string(env,_logfile).c_str());

    string jsonMusic;
    long WAIT_TIME = 3500000; //3.5s
    string status;
    for (;;) {
        jsonMusic = get_music_by_id(id);

        if(jsonMusic == ""){
            LOGD("Nothing return from get_music_by_id");
            logfile <<TAG<<"Nothing return from get_music_by_id" << endl;
            return env->NewStringUTF(jsonMusic.c_str());
        }
        string video_url;
//        string audio_url;
        auto jsonData = json::parse(jsonMusic);

        for (const auto &item: jsonData) {
//        string id = item.at("id").get<std::string>();
//        string title = item.at("title").get<std::string>();
//        string model_name = item.at("model_name").get<std::string>();
            status = item.at("status").get<std::string>();
            video_url = item.at("video_url").get<std::string>();
            LOGD("video %s", video_url.c_str());
            logfile <<TAG<< "Video url "<<video_url.c_str()<<endl;
//            audio_url = item.at("audio_url").get<std::string>();

        }

        if (status != "complete") { //not complete
            LOGD("music not complete,STATUS: %s", status.c_str());
            logfile<<TAG<<"music not complete,STATUS:"<<status.c_str() <<endl;
            usleep(WAIT_TIME);
            continue;
        } else {
            LOGD("music ready, STATUS: %s", status.c_str());
            logfile<<TAG<<"music ready, STATUS:" << status.c_str()<<endl;
            break;
        }
    }

    logfile.close();
    return env->NewStringUTF(jsonMusic.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_huawei_tony_rowinaimusic_MainActivity_nativeGetCreditLeft(JNIEnv *env, jobject thiz) {
    return env->NewStringUTF(get_limit().c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_aiberry_tony_rowinaimusic_LoginActivity_nativeGetCreditLeft(JNIEnv *env, jobject thiz) {
    string credit = get_limit();
    return env->NewStringUTF(get_limit().c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_aiberry_tony_rowinaimusic_jsondata_JsonData_nativeGetCreditLeft(JNIEnv *env,
                                                                         jobject thiz) {
    return env->NewStringUTF(get_limit().c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_aiberry_tony_rowinaimusic_ui_genmusic_GenMusicFragment_nativeGenMusic(JNIEnv *env,
                                                                               jobject thiz,
                                                                               jstring prompt, jstring _logfile) {

    string response;

    response = generate_music(jstring_to_string(env, prompt), jstring_to_string(env,_logfile));
    return env->NewStringUTF(response.c_str());
}