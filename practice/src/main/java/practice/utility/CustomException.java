package practice.utility;

public class CustomException extends Exception{

	public CustomException(String string){
		super(string);
	}
	
		public static class PayoutResponseException extends CustomException{
			
			public PayoutResponseException(String string) {
				// TODO Auto-generated constructor stub
				super(string);
			}
		}
		
		public static class QuotationNotFoundException extends CustomException{
			
			public QuotationNotFoundException(String string) {
				// TODO Auto-generated constructor stub
				super(string);
			}
		}
		
		public static class GetDueAPIResponseException extends CustomException{
			
			public GetDueAPIResponseException(String string) {
				// TODO Auto-generated constructor stub
				super(string);
			}
		}
		
		public static class FreelookFailedException extends CustomException{
			
			public FreelookFailedException(String string) {
				// TODO Auto-generated constructor stub
				super(string);
			}
		}
}
