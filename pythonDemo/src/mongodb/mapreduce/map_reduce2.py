# -*- coding: utf-8 -*-

from bson.code import Code
import pymongo

mapper = Code("""
            function() {
                var category;
                if ( this.age<25){
                    category = "young"
                }
                else{
                    category = "older"
                }
                emit(category,1);
            }
          """
          )

reducer = Code("""
                function(key,values){
                    var sum=0
                    for (var i = 0; i < values.length; i++) {
                        sum += values[i];
                    }
                    return sum;
                }
              """     
             )

conn = pymongo.Connection()
db = conn.local
result = db.users.map_reduce(mapper, reducer, "myresults")
for doc in result.find():
    print doc

