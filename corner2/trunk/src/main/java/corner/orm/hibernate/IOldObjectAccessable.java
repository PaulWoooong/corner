// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id: IOldObjectAccessable.java 3678 2007-11-14 04:43:52Z jcai $
// created at:2006-11-17
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package corner.orm.hibernate;

/**
 * 对hibernate的旧对象可读.
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision: 3678 $
 * @since 2.3
 */
public interface IOldObjectAccessable {
	/**
	 * 当load完毕实体的进行的操作
	 *
	 */
	public void initOldObject();
}
