package io.weli.testdome;
// https://www.testdome.com/library?page=1&skillArea=30&questionId=88836
// https://github.com/liweinan/deepseek-answers/blob/main/files/testdome-song.md
public class Song {
    private String name;
    private Song nextSong;

    public Song(String name) {
        this.name = name;
    }

    public void setNextSong(Song nextSong) {
        this.nextSong = nextSong;
    }

    public boolean isInRepeatingPlaylist() {
        // Handle edge case: if no next song, playlist cannot be repeating
        if (this.nextSong == null) {
            return false;
        }

        // Initialize slow and fast pointers
        Song slow = this;
        Song fast = this.nextSong;

        // Move slow pointer by 1 and fast pointer by 2 until they meet or reach null
        while (fast != null && fast.nextSong != null) {
            if (slow == fast) {
                return true; // Cycle detected
            }
            slow = slow.nextSong;
            fast = fast.nextSong.nextSong;
        }

        // If fast reaches null, no cycle exists
        return false;
    }

    public static void main(String[] args) {
        Song first = new Song("Hello");
        Song second = new Song("Eye of the tiger");
        first.setNextSong(second);
        second.setNextSong(first);
        System.out.println(first.isInRepeatingPlaylist()); // Prints: true
    }
}