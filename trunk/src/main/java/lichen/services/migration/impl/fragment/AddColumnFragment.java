/* 
 * Copyright 2008 The Lichen Team.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package lichen.services.migration.impl.fragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lichen.services.migration.ConnectionAdapter;

import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.mapping.Table;
import org.hibernate.tool.hbm2ddl.TableMetadata;

/**
 * 增加列的片段
 * @author <a href="jun.tsai@ouriba.com">Jun Tsai</a>
 * @version $Revision: 5182 $
 * @since 0.0.2
 */
public class AddColumnFragment extends AbstractMigrateFragment {

	public AddColumnFragment(ConnectionAdapter adapter,Dialect dialect,
			SessionFactoryImplementor sessionFactory, String tableName) {
		super(adapter, dialect, sessionFactory, tableName);
	}

	/**
	 * @see lichen.services.migration.MigrateFragment#generateMigrationFragments(org.hibernate.mapping.Table)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> generateMigrationFragments(Table table, TableMetadata tableInfo) {
		List<String> script = new ArrayList<String>();
		Iterator<String> subiter = table.sqlAlterStrings(getDialect(), getSessionFactory(),tableInfo, defaultCatalog, defaultSchema);
		while (subiter.hasNext()) {
			script.add(subiter.next());
		}
		return script;
	}

}