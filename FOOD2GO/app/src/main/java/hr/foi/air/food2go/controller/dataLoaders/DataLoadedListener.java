package hr.foi.air.food2go.controller.dataLoaders;

public interface DataLoadedListener {
    void onDataLoaded(String message, String status, Object data);
}
