public class LegacyMain {

    @SuppressWarnings("deprecation")
    public static void main(String[] args) {

        // Create the subject
        LegacyWeatherData data =
                new LegacyWeatherData();

        // Create the observer
        LegacyDisplay display =
                new LegacyDisplay();

        // Register the observer
        data.addObserver(display);

        // Send weather measurements
        data.setMeasurements(
                26.0f,
                60.0f
        );

        data.setMeasurements(
                30.0f,
                75.0f
        );
    }
}