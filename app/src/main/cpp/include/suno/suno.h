//
// Created by tony on 17/8/2024.
//

#ifndef RESTDEMO_SUNO_H
#define RESTDEMO_SUNO_H
#include <string>
#include "curl/curl.h"
#include <iostream>
#include "nlohmann/json.hpp"
#include <android/log.h>
#include <unistd.h>
#include "util/util.h"

#define BASE_URL "https://aiberries.xyz/api/"

using std::string;


string get_limit(void);
string get_musics();
string get_music_by_id(string id);
string generate_music(string prompt, string logfile);
int suno_init();
CURLcode get_endpoint(string endpoint, string &resp);
CURLcode post_endpoint(string endpoint, string prompt, string &resp);

#endif //RESTDEMO_SUNO_H
