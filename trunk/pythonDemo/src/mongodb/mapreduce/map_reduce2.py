# -*- coding: utf-8 -*-

from bson.code import Code
from bson.son import SON
import pymongo

conn = pymongo.Connection()
db = conn.local

print db.users.aggregate([
         {"$group": { "_id": "$age", "count": {"$sum": 1}  }  },
         {"$sort": SON([("count", -1), ("_id", -1)] ) }
     ])

print db.users.aggregate([
         {"$group": { "_id": "$age", "count": {"$avg": "$age" }  }  },
         {"$sort": SON([("count", -1), ("_id", -1)] ) }
     ])

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


result = db.users.map_reduce(mapper, reducer, "myresults")
for doc in result.find():
    print doc

