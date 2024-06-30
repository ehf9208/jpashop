package jpabook;

import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jpabook.jpashop.domain.Item;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.QItem;
import jpabook.jpashop.domain.QMember;

import java.util.List;

/**
 * Hello world!
 *
 */
public class JpaMain
{
    public static void main( String[] args ) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpaShop");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            JPAQuery<Member> query = new JPAQuery<>(em);
            QMember qMember = QMember.member;   // 기본인스턴스 사용
            // QMember qMember = new QMember("m"); // 별칭사용

            query
                    .from(qMember)
                    .where(qMember.name.eq("회원1"))
                    .orderBy(qMember.name.desc())
                    .fetch();

            JPAQuery<Item> query1 = new JPAQuery<>(em);
            QItem qItem = QItem.item;

            query1.from(qItem)
                    .where(qItem.price.gt(20000))
                    .orderBy(qItem.price.desc(), qItem.stockQuantity.asc())
                    .offset(10L).limit(20L)
                    .fetch();

            JPAQuery<Item> query2 = new JPAQuery<>(em);
            QItem qItem2 = QItem.item;

//            query2.from(qItem2)
//                    .where(qItem2.price.gt(10000))
//                    .offset(10).limit(20)


            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
