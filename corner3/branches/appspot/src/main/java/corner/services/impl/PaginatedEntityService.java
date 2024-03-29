/*
 * Copyright 2008 The Fepss Pty Ltd.
 * site: http://www.fepss.com
 * file: $Id$
 * created at:2009-4-28
 */
package corner.services.impl;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.regex.Pattern;

import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import corner.model.PaginationList;
import corner.model.PaginationOptions;

/**
 * supported pagination entity service
 * @author <a href="jun.tsai@fepss.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.1
 */
public class PaginatedEntityService {
    private Logger logger = LoggerFactory.getLogger(PaginatedEntityService.class);
    private TypeCoercer typeCoercer;
    private HibernateTemplate template;

    public PaginatedEntityService(final HibernateTemplate template,final TypeCoercer typeCoercer){
        this.template =template;
        this.typeCoercer = typeCoercer;
    }

    /**
     * 
     * magic paginate method.
     * eg:
     * <code>
     *  options.setPage(2);
     *
     *
     *  paginate(Member.class,new Object[]{"email=?","asdf@asdf.net"},"userName desc",options)

     *  paginate(Member.class,"email='asdf@asdf.net'","userName desc",options)
     *
     *  List conditions = new ArrayList();
     *  conditions.add("userName=? and password=?");
     *  conditions.add(userName);
     *  conditions.add(password);
     *  paginate(Member.class,conditions,"userName desc",options)
     * 
     * </code>
     * Magic conditions query criteria
     * @param persistClass persistence class
     * @param conditions query criteria
     * @param order order by sql
     * @param options pagination options.
     * @return include result and totalRecord.
     */
    public PaginationList paginate(final Class<?> persistClass,final Object conditions,
                                   final String order,final PaginationOptions options){
        final Iterable con = typeCoercer.coerce(conditions, Iterable.class);

        return (PaginationList) this.template.execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                final Iterator it = con==null?null:con.iterator();

                String conditionHQL = buildConditionHQL(persistClass, it).toString();

                //query list
                final StringBuffer queryHQL= new StringBuffer(conditionHQL);
                appendOrder(queryHQL, order);
                Query query = session.createQuery(queryHQL.toString());

                //count query
                final StringBuffer countHQL= new StringBuffer(conditionHQL);
                countHQL.insert(0,"select count(*) ");
                Query countQuery = session.createQuery(countHQL.toString());
                
                if (it != null) {
                    int i = 0;
                    while (it.hasNext()) {
                        Object obj = it.next();
                        query.setParameter(i, obj);
                        countQuery.setParameter(i,obj);
                        i++;
                    }
                }
                //get perpage
                int perPage = options.getPerPage();
                int page = options.getPage();
                if(page<1){
                    page =1;
                }
                query.setFirstResult((page-1)*perPage);
                query.setMaxResults(perPage);
                PaginationList list = new PaginationList(query.iterate(),options);

                //query total record number
                options.setTotalRecord((Long) countQuery.iterate().next());
                return list;

            }
        });
    }
    public Iterator find(final Class<?> persistClass,final Object conditions,final String order){
       return (Iterator) this.template.execute(new HibernateCallback(){
           public Object doInHibernate(Session session) throws HibernateException, SQLException {
               Iterable con = typeCoercer.coerce(conditions, Iterable.class);
               final Iterator it = con==null?null:con.iterator();
               final StringBuffer sb = buildConditionHQL(persistClass, it);
               appendOrder(sb, order);
               Query query = session.createQuery(sb.toString());
               if(it!=null){
                   int i=0;
                  while(it.hasNext()) {
                      query.setParameter(i++,it.next());
                  }
               }
               return query.iterate();
           }
       });
    }

    void appendOrder(StringBuffer sb, String order) {
        if(order!=null){
            if(!Pattern.compile("^order\\s+").matcher(order.trim()).find()){ //no order keywords
                sb.append(" order by");
            }
            sb.append(" ").append(order.trim());
        }
    }

    public long count(final Class<?> persistClass,final Object conditions){
        return (Long) this.template.execute(new HibernateCallback(){
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Iterable con = typeCoercer.coerce(conditions, Iterable.class);
                final Iterator it = con==null?null:con.iterator();
                final StringBuffer sb = buildConditionHQL(persistClass, it);
                sb.insert(0,"select count(*) ");
                Query query = session.createQuery(sb.toString());
                if(it!=null){
                    int i=0;
                    while(it.hasNext()) {
                        query.setParameter(i++,it.next());
                    }
                }
                return query.iterate().next();
            }
        });
    }

    private StringBuffer buildConditionHQL(Class<?> persistClass, Iterator conditions) {
        final StringBuffer sb = new StringBuffer();
        sb.append("from ").append(persistClass.getName()).append(" as root ");
        if(conditions !=null&& conditions.hasNext()){
            String where = String.valueOf(conditions.next()).trim();
            
            if(!Pattern.compile("^where\\s+").matcher(where).find()){//no where keywords
                sb.append(" where");
            }
            sb.append(" ").append(where);
        }
        if(logger.isDebugEnabled()){
            logger.debug("construct HQL:["+sb.toString()+"]");
        }
        return sb;
    }
}
