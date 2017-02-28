delete from products
where not exists 
(select * from deliverydocuments d 
where deliverydocument = d.code)

update invoices set invoicedate = '2016-06-04' where progressive > 19 and progressive < 25