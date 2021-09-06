import re

word_freq = {}
with open('../20_files_word_frequency.txt', encoding='utf-8') as f:
    for l in f:
        l_tuple = l.split()
        if len(l_tuple) != 2 or '“' in l_tuple[0] or '”' in l_tuple[0]:
            continue
        word = re.sub(r'[^\w\s]', '', l_tuple[0].strip(r'[^\w\s]'))
        word_freq[l_tuple[0]] = int(l_tuple[1])

top_words = sorted(word_freq.items(), key=lambda item: item[1], reverse=True)[:100]

f = open('../top_100_words.txt', 'w')
for pair in top_words:
    f.write(pair[0] + ' ' + str(pair[1]) + '\n')
f.close()