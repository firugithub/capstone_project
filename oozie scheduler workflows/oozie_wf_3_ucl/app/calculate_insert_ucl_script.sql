insert into table card_lookup_table_hive 
select * from (
SELECT t.card_id as card_id,cast(t.ucl as double) ucl,null postcode,lt.last_modified transaction_dt,ct.score score
FROM
  (SELECT card_id,
          AVG(amount)+3 * STDDEV(amount) AS ucl
   FROM
     (SELECT *
      FROM
        (SELECT card_id,
                transaction_dt,
                amount,
                rank() over (partition BY card_id
                             ORDER BY transaction_dt) rn
         FROM card_transactions_hive) dt
      WHERE dt.rn<=10 ) dt1
   GROUP BY card_id) t
JOIN
  (
  SELECT c.card_id,
          m.member_id,
          m.score,
          c.city
   FROM card_member_hive c
   JOIN member_score_hive m ON c.member_id=m.member_id
   ) ct ON t.card_id = ct.card_id
   
 JOIN
  (
  select sub.card_id, from_unixtime(sub.last_modified,'yyyy-MM-dd HH:mm:ss') last_modified
    from ( select card_id, transaction_dt,
              max( unix_timestamp(transaction_dt)) over (partition by card_id) as last_modified 
       from card_transactions_hive ) as sub
    where   
    unix_timestamp(sub.transaction_dt) = sub.last_modified
  ) lt ON t.card_id = lt.card_id
 ) t1;