//
// Created by tony on 17/8/2024.
//
#include <string>
#include "curl/curl.h"
#include <iostream>
#include "nlohmann/json.hpp"
#include <android/log.h>
#include <unistd.h>
#include "suno/suno.h"
#include "util/util.h"

#define TAG "AIMUSIC" // 这个是自定义的LOG的标识
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,TAG ,__VA_ARGS__) // 定义LOGD类型
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG ,__VA_ARGS__) // 定义LOGI类型
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN,TAG ,__VA_ARGS__) // 定义LOGW类型
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,TAG ,__VA_ARGS__) // 定义LOGE类型
#define LOGF(...) __android_log_print(ANDROID_LOG_FATAL,TAG ,__VA_ARGS__) // 定义LOGF类型

using nlohmann::json;
using std::string;
//extern string BASE_URL;
int suno_init(){
    return 1;
}

CURLcode get_endpoint(string endpoint, string &resp) {
    CURL *curl;
    CURLcode res;
    string response;
    string ulr = BASE_URL;
    ulr = ulr + endpoint;

    LOGD("%s",ulr.c_str());

    curl = curl_easy_init();
    if (NULL == curl) {
        LOGD("Curl created failed");
        return CURLE_FAILED_INIT;
    } else {
        curl_easy_setopt(curl, CURLOPT_SSL_VERIFYPEER, 0L); //disable SSL for test
        curl_easy_setopt(curl, CURLOPT_SSL_VERIFYHOST, 0L);
        curl_easy_setopt(curl, CURLOPT_URL, ulr.c_str());
        curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, WriteCallback);
        curl_easy_setopt(curl, CURLOPT_WRITEDATA, &response);
        res = curl_easy_perform(curl);
        if (res != CURLE_OK) {
            LOGD("Curl request failed: %d", res);
            return res;
        }
        curl_easy_cleanup(curl);
    }

    LOGD("%s",response.c_str());
    resp=response;

    return CURLE_OK;
}


CURLcode post_endpoint(string endpoint, string prompt, string &resp) {
    CURL *curl;
    CURLcode res;
    string response;
    //std::string response;
    string url = BASE_URL;
    url = url + endpoint;

    LOGD("%s", url.c_str());


curl = curl_easy_init();
    if (curl) {
        json requestData;
        requestData["model"] = "chirp-v3-5";
        requestData["make_instrumental"] = false;
        requestData["prompt"] =prompt;
        requestData["wait_audio"] = false;
        std::string requestDataStr = requestData.dump().c_str();
        struct curl_slist *headers = NULL;
        headers = curl_slist_append(headers, "Content-Type: application/json");
        //headers = curl_slist_append(headers, ("Authorization: Bearer " + apiKey).c_str());

        curl_easy_setopt(curl, CURLOPT_SSL_VERIFYPEER, 0L);
        curl_easy_setopt(curl, CURLOPT_SSL_VERIFYHOST, 0L);

        curl_easy_setopt(curl, CURLOPT_URL, url.c_str());
        curl_easy_setopt(curl, CURLOPT_POSTFIELDS, requestDataStr.c_str());
        curl_easy_setopt(curl, CURLOPT_POSTFIELDSIZE, requestDataStr.length());
        curl_easy_setopt(curl, CURLOPT_HTTPHEADER, headers);
        curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, WriteCallback);
        curl_easy_setopt(curl, CURLOPT_WRITEDATA, &response);
        res = curl_easy_perform(curl);
        if (res != CURLE_OK) {
            LOGD("Curl request failed. error code: %d , reason: %s", res, curl_easy_strerror(res));
            return res;
        }
        curl_easy_cleanup(curl);
        curl_slist_free_all(headers);
    }

    LOGD("%s",response.c_str());
    resp= response;
    LOGD("%s",resp.c_str());

    return CURLE_OK;
}

std::string get_limits(void)
{
    CURL *curl;
    CURLcode res;
    std::string response;

    curl = curl_easy_init();
    if(NULL == curl){
        LOGD("Curl created failed");
        return "Failed";
    }
    else {
        curl_easy_setopt(curl, CURLOPT_SSL_VERIFYPEER, 0L); //disable SSL for test
        curl_easy_setopt(curl, CURLOPT_SSL_VERIFYHOST, 0L);
        curl_easy_setopt(curl, CURLOPT_URL, "https://aiberries.xyz/api/get_limit");
        curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, WriteCallback);
        curl_easy_setopt(curl, CURLOPT_WRITEDATA, &response);
        res = curl_easy_perform(curl);
        if (res != CURLE_OK) {
            LOGD("Curl request failed: %d", res);
            return "Failed";
        }
        curl_easy_cleanup(curl);

        LOGD("%s", response.c_str());

        return response;

    }
}

string get_music_by_id(string id){
    LOGD("%s",id.c_str());
    string ret;
    string response;
    string endpoint = "get?ids="+id;
    CURLcode code= get_endpoint(endpoint, response);
    if(code != CURLE_OK){
        LOGD("request faile, code %d",code);
        ret = "request failed";
    }
    else
    {
        ret = response;
    }

    return ret;
}


string get_musics(){
    string ret;
    string response;
    CURLcode code= get_endpoint("get", response);
    if(code != CURLE_OK){
        LOGD("request faile, code %d",code);
        ret = "request failed";
    }
    else
    {
        ret = response;
    }

    return ret;
}

string get_limit(){
    string ret;
    string response;
    CURLcode code= get_endpoint("get_limit", response);
    if(code != CURLE_OK){
        LOGD("request faile, code %d",code);
        ret = "request failed";
    }
    else
    {
        ret = response;
    }

    return ret;
}

string generate_music(string prompt){
    string ret;
    string response;
    CURLcode code= post_endpoint("generate", prompt,response);
    if(code != CURLE_OK){
        LOGD("request faile, code %d",code);
        ret = "request failed";
    }
    else
    {
        ret = response;
    }

    LOGD("%s",ret.c_str());
    return ret;
}