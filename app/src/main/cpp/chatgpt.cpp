//
// Created by tony on 17/8/2024.
//
#include "include/chatgpt/chatgpt.h"
#include "include/util/util.h"
using nlohmann::json;
std::string chatentry() { //doesn't work now
    std::string prompt = "please write a poem about summer";
    LOGD("%s", prompt.c_str());

    std::string response = getCompletion(prompt);
    LOGD("%s", response.c_str());
    return response.c_str();
}
//
//// Handle data received from the server
//size_t WriteCallback(void *contents, size_t size, size_t nmemb, std::string *response) {
//    size_t totalSize = size * nmemb;
//    response->append((char *) contents, totalSize);
//    return totalSize;
//}
//
// Construct a POST request to the chat model endpoint and process the response.
std::string getCompletion(const std::string &prompt) {//, const string& model = "gpt-4o-mini") {
    std::string apiKey = ";
    std::string baseUrl = "https://api.openai.com/v1/chat/completions";
    std::string response;
    CURL *curl = curl_easy_init();
    if (curl) {
        json requestData;
        requestData["model"] = "gpt-4o-mini";
        requestData["messages"][0]["role"] = "user";
        requestData["messages"][0]["content"] =prompt;
        requestData["temperature"] = 0;
        std::string requestDataStr = requestData.dump().c_str();
        struct curl_slist *headers = NULL;
        headers = curl_slist_append(headers, "Content-Type: application/json");
        headers = curl_slist_append(headers, ("Authorization: Bearer " + apiKey).c_str());

        curl_easy_setopt(curl, CURLOPT_SSL_VERIFYPEER, 0L);
        curl_easy_setopt(curl, CURLOPT_SSL_VERIFYHOST, 0L);

        curl_easy_setopt(curl, CURLOPT_URL, baseUrl.c_str());
        curl_easy_setopt(curl, CURLOPT_POSTFIELDS, requestDataStr.c_str());
        curl_easy_setopt(curl, CURLOPT_POSTFIELDSIZE, requestDataStr.length());
        curl_easy_setopt(curl, CURLOPT_HTTPHEADER, headers);
        curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, WriteCallback);
        curl_easy_setopt(curl, CURLOPT_WRITEDATA, &response);
        CURLcode res = curl_easy_perform(curl);
        if (res != CURLE_OK) {
            LOGD("Curl request failed: %s", curl_easy_strerror(res));
            return "Failed";
            //std::cerr << "Curl request failed: " << curl_easy_strerror(res) << std::endl;
        }
        curl_easy_cleanup(curl);
        curl_slist_free_all(headers);
    }
    // return response;


    LOGD("%s",response.c_str());

    json jresponse = json::parse(response);
    return jresponse["choices"][0]["message"]["content"].get<std::string>();
}
