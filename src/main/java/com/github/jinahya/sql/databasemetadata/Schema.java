/*
 * Copyright 2011 Jin Kwon <jinahya at gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.github.jinahya.sql.databasemetadata;


import com.github.jinahya.sql.databasemetadata.ColumnLabel;
import com.github.jinahya.sql.databasemetadata.ColumnRetriever;
import com.github.jinahya.sql.databasemetadata.Suppression;
import com.github.jinahya.sql.databasemetadata.SuppressionPath;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


/**
 *
 * @author Jin Kwon <jinahya at gmail.com>
 */
@XmlType(
    propOrder = {
        "tableSchem", "functions", "procedures", "tables", "userDefinedTypes"
    }
)
public class Schema implements Comparable<Schema> {


    public static final String SUPPRESSION_PATH_FUNCTIONS
        = "schema/functions";


    public static final String SUPPRESSION_PATH_PROCEDURES
        = "schema/procedures";


    public static final String SUPPRESSION_PATH_TABLES
        = "schema/tables";


    public static final String SUPPRESSION_PATH_USER_DEFINED_TYPES
        = "schema/userDefinedTypes";


    /**
     *
     * @param suppression
     * @param resultSet
     *
     * @return
     *
     * @throws SQLException
     */
    public static Schema retrieve(final Suppression suppression,
                                  final ResultSet resultSet)
        throws SQLException {

        Objects.requireNonNull(suppression, "null suppression");

        Objects.requireNonNull(resultSet, "null resultSet");

        final Schema instance = new Schema();

        ColumnRetriever.retrieve(
            Schema.class, instance, suppression, resultSet);

        return instance;
    }


    /**
     *
     * @param database
     * @param suppression
     * @param catalog
     * @param schemaPattern
     * @param schemas
     *
     * @throws SQLException
     *
     * @see DatabaseMetaData#getSchemas(java.lang.String, java.lang.String)
     */
    public static void retrieve(final DatabaseMetaData database,
                                final Suppression suppression,
                                final String catalog,
                                final String schemaPattern,
                                final Collection<? super Schema> schemas)
        throws SQLException {

        Objects.requireNonNull(database, "null database");

        Objects.requireNonNull(suppression, "null suppression");

        Objects.requireNonNull(catalog, "null schemas");

        if (suppression.isSuppressed(Catalog.SUPPRESSION_PATH_SCHEMAS)) {
            return;
        }

        final ResultSet resultSet
            = database.getSchemas(catalog, schemaPattern);
        try {
            while (resultSet.next()) {
                final Schema instance = retrieve(suppression, resultSet);
                schemas.add(instance);
            }
        } finally {
            resultSet.close();
        }
    }


    /**
     *
     * @param database
     * @param suppression
     * @param catalog
     *
     * @throws SQLException
     */
    public static void retrieve(final DatabaseMetaData database,
                                final Suppression suppression,
                                final Catalog catalog)
        throws SQLException {

        Objects.requireNonNull(database, "null database");

        Objects.requireNonNull(suppression, "null suppression");

        Objects.requireNonNull(catalog, "null catalog");

        retrieve(database, suppression,
                 catalog.getTableCat(), null,
                 catalog.getSchemas());

        if (catalog.getSchemas().isEmpty()) {
            final Schema instance = new Schema();
            instance.setTableSchem("");
            catalog.getSchemas().add(instance);
        }

        for (final Schema child : catalog.getSchemas()) {
            child.setCatalog(catalog);
        }

        for (final Schema instance : catalog.getSchemas()) {
            Function.retrieve(database, suppression, instance);
            Procedure.retrieve(database, suppression, instance);
            Table.retrieve(database, suppression, instance);
            UserDefinedType.retrieve(database, suppression, instance);
        }
    }


    /**
     * Creates a new instance.
     */
    public Schema() {

        super();
    }


    @Override
    public int compareTo(final Schema o) {

        Objects.requireNonNull(o, "null ofject");

        final int catalogCompared = catalog.compareTo(o.catalog);
        if (catalogCompared != 0) {
            return catalogCompared;
        }

        return tableSchem.compareTo(o.tableSchem);
    }


    // ----------------------------------------------------------------- catalog
    public Catalog getCatalog() {

        return catalog;
    }


    public void setCatalog(final Catalog catalog) {

        this.catalog = catalog;
    }


    // -------------------------------------------------------------------- name
    /**
     * Return the name of this schema.
     *
     * @return the name
     */
    public String getTableSchem() {

        return tableSchem;
    }


    public String getName() {

        return getTableSchem();
    }


    /**
     * Replaces the name of this schema.
     *
     * @param tableSchem the name
     */
    public void setTableSchem(final String tableSchem) {

        this.tableSchem = tableSchem;
    }


    public void setName(final String name) {

        setTableSchem(name);
    }


    // --------------------------------------------------------------- functions
    public List<Function> getFunctions() {

        if (functions == null) {
            functions = new ArrayList<Function>();
        }

        return functions;
    }


    // -------------------------------------------------------------- procedures
    public List<Procedure> getProcedures() {

        if (procedures == null) {
            procedures = new ArrayList<Procedure>();
        }

        return procedures;
    }


    // ------------------------------------------------------------------ tables
    /**
     * Returns a list of tables.
     *
     * @return tables
     */
    public List<Table> getTables() {

        if (tables == null) {
            tables = new ArrayList<Table>();
        }

        return tables;
    }


    public List<String> getTableNames() {

        final List<String> tableNames
            = new ArrayList<String>(getTables().size());

        for (final Table table : getTables()) {
            tableNames.add(table.getTableName());
        }

        return tableNames;
    }


    public Table getTableByName(final String tableName) {

        Objects.requireNonNull(tableName, "null tableName");

        for (final Table table : getTables()) {
            if (tableName.equals(table.getTableName())) {
                return table;
            }
        }

        return null;
    }


    // -------------------------------------------------------- userDefinedTypes
    public List<UserDefinedType> getUserDefinedTypes() {

        if (userDefinedTypes == null) {
            userDefinedTypes = new ArrayList<UserDefinedType>();
        }

        return userDefinedTypes;
    }


    @ColumnLabel("TABLE_CATALOG")
    @SuppressionPath("schema/tableCatalog")
    @XmlAttribute
    private String tableCatalog;


    @XmlTransient
    private Catalog catalog;


    @ColumnLabel("TABLE_SCHEM")
    //@SuppressionPath("schema/tableSchem")
    @XmlElement(required = true)
    private String tableSchem;


    @SuppressionPath(SUPPRESSION_PATH_FUNCTIONS)
    @XmlElement(name = "function")
    private List<Function> functions;


    @SuppressionPath(SUPPRESSION_PATH_PROCEDURES)
    @XmlElement(name = "procedure")
    private List<Procedure> procedures;


    @SuppressionPath(SUPPRESSION_PATH_TABLES)
    @XmlElement(name = "table")
    private List<Table> tables;


    @SuppressionPath(SUPPRESSION_PATH_USER_DEFINED_TYPES)
    @XmlElement(name = "userDefinedType")
    private List<UserDefinedType> userDefinedTypes;


}

