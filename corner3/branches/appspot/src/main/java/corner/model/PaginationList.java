/*
 * Copyright 2008 The Fepss Pty Ltd.
 * site: http://www.fepss.com
 * file: $Id$
 * created at:2009-4-26
 */
package corner.model;

/**
 * provide pagination function list based on ArrayList Object
 * @author <a href="jun.tsai@fepss.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.1
 */
public class PaginationList <T>{
    private Object it;
    private PaginationOptions options;

    /**
     * construct pagination list by iterator
     * @param it collection object
     **/
    public PaginationList(Object it, PaginationOptions options){
        this.it = it;
        this.options = options;
    }

    public Object collectionObject() {
        return it;
    }
    public PaginationOptions options(){
        return this.options;
    }
}
