package hr.foi.air.webservice;

public interface WebServiceHandler {
    void onDataArrived(String message, String status,Object data);
}
