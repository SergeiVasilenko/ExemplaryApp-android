package com.sergeivasilenko.exemplary.utils.log;

/**
 * Created on 30.11.16.
 *
 * @author Sergey Vasilenko (vasilenko.sn@gmail.com)
 */
public interface LogPrinter {
	void printLog(int priority, String tag, String message, Throwable t);
}
