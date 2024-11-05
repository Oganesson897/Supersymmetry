from openai import OpenAI

client = OpenAI(api_key="sk-gptx6qMXJBiy2Hla5MEiiwrWQYeQgDPD2XapHQnkqcT05F3q", base_url="https://api.gptdos.com/v1/")
with open("./en_us.lang", encoding="utf-8") as en_us:
    i = 0
    for line in en_us.readlines():
        i += 1
        if i < 670: continue
        key, value = line.split("=", 1)
        with open("./zh_cn.lang", "a", encoding="utf=8") as zh_cn:
            if (value == "No Description" or key.endswith("title")):
                zh_cn.write(key + "=" + value + "\n")
            else:
                response = client.chat.completions.create(
                    messages=[
                        {
                            "role" : "system",
                            "content" : "直接回复将后文翻译为中文后的文本，保留格式化字符，如%n"
                        },
                        {
                            "role" : "user",
                            "content" : value
                        }
                    ],
                    model="gpt-3.5-turbo"
                )
                zh_cn.write(key + "=" + response.choices[0].message.content + "\n")