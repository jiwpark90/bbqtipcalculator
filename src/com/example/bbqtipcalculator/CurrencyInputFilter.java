package com.example.bbqtipcalculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * InputFilter that filters text for currency input.
 *
 * @author Ji jiwpark90
 */
public class CurrencyInputFilter implements InputFilter {
	
	// TODO make this configurable in the settings
	static int MAX_LENGTH = 3;

	// regex pattern to match
	Pattern mPattern = Pattern.compile("(0|[1-9]+[0-9]*)?(\\.[0-9]{0,2})?");

	@Override
	public CharSequence filter(CharSequence source, int start, int end,
			Spanned dest, int dstart, int dend) {

		String result =	dest.subSequence(0, dstart)	+ source.toString()	+
				dest.subSequence(dend, dest.length());
		
		// check to see if the tip exceeds the maximum usual amount (3 = hundreds)
		String[] splitResult = result.split("\\.");
		if (splitResult.length != 0 && splitResult[0].length() > MAX_LENGTH) {
			return dest.subSequence(dstart, dend);
		}

		try {
			// check if the entered amount is beyond what the system can handle
			if (Double.parseDouble(result) >=
					Integer.MAX_VALUE / MainActivity.US_DOLLAR_IN_CENTS) {
				return dest.subSequence(dstart, dend);
			}
		} catch (NumberFormatException e) {
			// not yet a number; continue matching it against the pattern
		}

		Matcher matcher = mPattern.matcher(result);

		if (!matcher.matches()) {
			return dest.subSequence(dstart, dend);
		}

		return null;
	}
}