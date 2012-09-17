/*
   Copyright 2012 Przemysław Pastuszka

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
 
package pl.rtshadow.jtriss.schema;

import java.util.ArrayList;

import org.apache.commons.lang3.tuple.Pair;

public class Schema extends ArrayList<Pair<Class, ColumnType>> {
  private static final long serialVersionUID = 2779832184835370214L;

  public Schema addColumn(Class clazz, ColumnType type) {
    add(Pair.of(clazz, type));
    return this;
  }
}
