// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id: DataSqueezerFilter.java 3678 2007-11-14 04:43:52Z jcai $
// created at:2006-05-26
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

package corner.orm.tapestry.data;

import org.apache.tapestry.services.DataSqueezer;

/**
 * 重构部分代码，使其能够采用一串的datasqueezer。
 * @author Jun Tsai
 * @version $Revision: 3678 $
 * @since 2.0.5
 */
public interface  DataSqueezerFilter {
	String squeeze( Object object, DataSqueezer next );

    String[] squeeze( Object[] objects, DataSqueezer next );

    Object unsqueeze( String string, DataSqueezer next );

    Object[] unsqueeze( String[] strings, DataSqueezer next );
}
