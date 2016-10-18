package sami.example.com.googlelogin.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import android.support.design;

import sami.example.com.googlelogin.R;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    static String[] carNames = {"amaze", "aspire", "city", "ford", "honda civic", "indica", "scorpio", "suzuki", "swift", "toyota"};
    static Integer[] carImages = {R.drawable.amaze, R.drawable.aspire, R.drawable.city, R.drawable.ford, R.drawable.honda_civic, R.drawable.indica, R.drawable.scorpio, R.drawable.suzuki, R.drawable.swift, R.drawable.toyota};
    static String[] carRent = {"$33.92/hr", "$12.11/hr", "$5.0/hr", "$9.98/hr", "$4.35/hr", "$2.99/hr", "$5.68/hr", "$6.68/hr", "$3.43/hr", "$8.75/hr"};
    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    private static final int COUNT = carNames.length;

    static {
        // Add some sample items.
        for (int i = 0; i < COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static DummyItem createDummyItem(int position) {
        return new DummyItem(String.valueOf(position), makeContent(position), makeDetails(position), getImageId(position));
    }

    private static String makeDetails(int position) {
        return carRent[position];
    }

    private static String makeContent(int position) {
        return carNames[position];
    }

    private static String getImageId(int position) {
        return Integer.toString(carImages[position]);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final String id;
        public final String image;
        public final String content;
        public final String details;

        public DummyItem(String id, String content, String details, String image) {
            this.id = id;
            this.content = content;
            this.details = details;
            this.image = image;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
