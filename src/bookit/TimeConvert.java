package bookit;

public class TimeConvert {

	public String convertTime(String primeFacesDate) {

		String convertedDate;

		int wordLength = primeFacesDate.length();

		String year = primeFacesDate.substring(wordLength - 4, wordLength);
		String month = primeFacesDate.substring(4, 7);
		String monthStr = convertMonth(month);

		String day = primeFacesDate.substring(8, 10);
		String hour = primeFacesDate.substring(11, 13);
		String minute = primeFacesDate.substring(14, 16);

		convertedDate = year + "-" + monthStr + "-" + day + " " + hour + ":" + minute;

		return convertedDate;
	}

	private String convertMonth(String month) {

		String monthString;
		switch (month) {
		case "Jan":
			monthString = "01";
			break;
		case "Feb":
			monthString = "02";
			break;
		case "Mar":
			monthString = "03";
			break;
		case "Apr":
			monthString = "04";
			break;
		case "May":
			monthString = "05";
			break;
		case "Jun":
			monthString = "06";
			break;
		case "Jul":
			monthString = "07";
			break;
		case "Aug":
			monthString = "08";
			break;
		case "Sep":
			monthString = "09";
			break;
		case "Oct":
			monthString = "10";
			break;
		case "Nov":
			monthString = "11";
			break;
		case "Dec":
			monthString = "12";
			break;
		default:
			monthString = "Invalid month";
			break;
		}
		return monthString;
	}
}
