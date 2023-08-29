# Transaccions

Documentació sobre transaccions:

    [ACID control with transactions](https://mariadb.com/kb/en/acid-concurrency-control-with-transactions/)
    [Transaction management](https://docs.oracle.com/cd/B19306_01/server.102/b14220/transact.htm)

Per defecte una *Connection* es crea en mode **auto-commit**. Això implica que les sentències sql s'executen una a una a mesura que s'envien. Es pot canviar el mode perquè només s'executin en fer **commit** a la transacció,

A partir d'una *Connection* podem usar els següents mètodes:

  * **void setAutoCommit(boolean autoCommit)**: Sets this connection's auto-commit mode to the given state.
  * **boolean getAutoCommit()**: Retrieves the current auto-commit mode for this Connection object.
  * **void commit()**: Makes all changes made since the previous commit/rollback permanent and releases any database locks currently held by this Connection object
  * **void rollback()**: Undoes all changes made in the current transaction and releases any database locks currently held by this Connection object.
