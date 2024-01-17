package at.debugtools;

import java.text.MessageFormat;

import static at.debugtools.DebugColors.*;

public class DebugTools {
  public static String getFile(Throwable th, String color) { return color + th.getStackTrace()[0].getFileName() + ANSI_RESET; }
  public static String getClass(Throwable th, String color) { return color + th.getStackTrace()[0].getClassName() + ANSI_RESET; }
  public static String getMethod(Throwable th, String color) { return color + th.getStackTrace()[0].getMethodName() + ANSI_RESET; }
  public static String getLineNumber(Throwable th, String color) { return color + th.getStackTrace()[0].getLineNumber() + ANSI_RESET; }
  public static String debugLine(Throwable th, String color) { return MessageFormat.format("{0}{1}::{2}::{3}{4}${5} ", color, getClass(th, color), getMethod(th, color), getLineNumber(th, color), ANSI_YELLOW, ANSI_RESET); }
  public static String getAll(Throwable th, String color) { return MessageFormat.format("{0}{1}::{2}::{3}::{4}{5}${6} ", color, getFile(th, color), getClass(th, color), getMethod(th, color), getLineNumber(th, color), ANSI_YELLOW, ANSI_RESET); }

  public static String getFile(Throwable th) { return ANSI_RED + th.getStackTrace()[0].getFileName() + ANSI_RESET; }
  public static String getClass(Throwable th) { return ANSI_RED + th.getStackTrace()[0].getClassName() + ANSI_RESET; }
  public static String getMethod(Throwable th) { return ANSI_RED + th.getStackTrace()[0].getMethodName() + ANSI_RESET; }
  public static String getLineNumber(Throwable th) { return ANSI_RED + th.getStackTrace()[0].getLineNumber() + ANSI_RESET; }
  public static String debugLine(Throwable th) { return MessageFormat.format("{0}{1}::{2}::{3}{4}${5} ", ANSI_RED, getClass(th), getMethod(th), getLineNumber(th), ANSI_YELLOW, ANSI_RESET); }
  public static String getAll(Throwable th) { return MessageFormat.format("{0}{1}::{2}::{3}::{4}{5}${6} ", ANSI_RED, getFile(th), getClass(th), getMethod(th), getLineNumber(th), ANSI_YELLOW, ANSI_RESET); }
}
