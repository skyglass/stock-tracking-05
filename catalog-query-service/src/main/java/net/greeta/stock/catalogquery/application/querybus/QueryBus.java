package net.greeta.stock.catalogquery.application.querybus;

public interface QueryBus {
  <R, Q extends Query<R>> R execute(Q query);
}
