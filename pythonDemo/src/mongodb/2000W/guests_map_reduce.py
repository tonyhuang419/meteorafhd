# -*- coding: utf-8 -*-

from bson.code import Code
from bson.son import SON
import pymongo

conn = pymongo.Connection()
db = conn.local


# print db.guests.aggregate([
#          {"$group": { "_id": "$ctfId", "count": {"$sum": 1}  }  },
#          {"$sort": SON([("count", -1), ("_id", -1)] ) }
#      ])

from bson.code import Code
mapper = Code("""
               function () {
                   emit( this.ctfId , 1);
               }
               """)

reducer = Code("""
                function (key, values) {
                  var total = 0;
                  for (var i = 0; i < values.length; i++) {
                    total += values[i];
                  }
                  return total;
                }
                """)

result = db.guests.map_reduce(mapper, reducer, "myresults")
for doc in result.find():
    if doc['value'] and doc['value'] >1 :
        print doc
