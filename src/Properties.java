import java.util.ArrayList;

public class Properties {
    public static String getTitle() {
        String title = "";
        ArrayList<String> properties = Reader.propertiesReader();
        for (String line : properties) {
            if (line.startsWith("title")){
                String[] titleLine = line.split("=");
                title = titleLine[1];
            }
        } return title;
    }

    public static int getDiscountPercentage() {
        int discountPercentage = 0;
        ArrayList<String> properties = Reader.propertiesReader();
        for (String line : properties) {
            if (line.startsWith("discount-percentage")){
                String[] titleLine = line.split("=");
                discountPercentage = Integer.parseInt(titleLine[1]);
            }
        } return discountPercentage;
    }

    public static int getBlockTime() {
        int blockTime = 0;
        ArrayList<String> properties = Reader.propertiesReader();
        for (String line : properties) {
            if (line.startsWith("block-time")){
                String[] titleLine = line.split("=");
                blockTime = Integer.parseInt(titleLine[1]);
            }
        } return blockTime;
    }

    public static int getMaxErrorWithoutGetBlocked() {
        int maxError = 0;
        ArrayList<String> properties = Reader.propertiesReader();
        for (String line : properties) {
            if (line.startsWith("maximum-error-without-getting-blocked")){
                String[] titleLine = line.split("=");
                maxError = Integer.parseInt(titleLine[1]);
            }
        } return maxError;
    }
}
