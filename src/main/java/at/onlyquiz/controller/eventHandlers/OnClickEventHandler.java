package at.onlyquiz.controller.eventHandlers;

@FunctionalInterface
public interface OnClickEventHandler<T> {
  void onCLick(T t);
}
