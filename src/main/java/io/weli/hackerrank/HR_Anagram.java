package io.weli.hackerrank;

import java.util.HashMap;
import java.util.Map;

// https://www.hackerrank.com/challenges/anagram/problem
public class HR_Anagram {

    /*
    int anagram(string s) {
    if(s.size() % 2 == 1) return -1;
    map<char, int> mp;
    int ans = 0;
    for(int i = 0; i < s.size() / 2; i++) mp[s[i]]++;
    for(int i = s.size() / 2; i < s.size(); i++){
        if(mp[s[i]] != 0) mp[s[i]]--;
        else ans++;
    }
    return ans;
}
     */
    // https://www.youtube.com/watch?v=0-xHzWDVAME&feature=youtu.be
    public static int anagram(String s) {
        // Write your code here
        if (s.length() % 2 == 1) return -1;

        Map<Character, Integer> map = new HashMap();
        int ans = 0;

        for(int i = 0; i < s.length() / 2; i++) {
                map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);
        }

        for (int i = s.length() / 2; i < s.length(); i++) {
            if (map.getOrDefault(s.charAt(i), 0) > 0) map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) - 1);
            else ans++;
        }

        return ans;
    }

    public static void main(String[] args) {

    }
}
