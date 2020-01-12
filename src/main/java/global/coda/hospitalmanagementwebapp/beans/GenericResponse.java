package global.coda.hospitalmanagementwebapp.beans;

public class GenericResponse<T> {
	int statusCode;
	T data;

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
