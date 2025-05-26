use tutorial;
// db.users.drop()
// users 컬렉션에 username이 smith인 문서를 저장하세요.
db.users.insertOne({username: "smith"});

//위의 문서를 다음과 같이 되도록 수정하세요.
db.users.updateOne( {username: "smith"},
    {
        $set: {
            favorites: {
                cities: ["Chicago", "Cheyenne"],
                movies: ["Casablanca", "For a Few Dollars More", "The Sting"]
            }
        }
    })

//users 컬렉션에 {username: "jones"} 문서를 추가하세요.
db.users.insertOne({username: "jones"});

// 위의 문서를 다음과 같이 되도록 수정하세요.
db.users.updateOne( {username: "jones"},
    {
        $set: {
            favorites: {
                movies: ["Casablanca", "Rocky"]
            }
        }
    })

// db.users.find().pretty()



//users 컬렉션의 "Casablanca" 영화를 좋아하는 사용자들을 출력하세요
db.users.find({"favorites.movies": "Casablanca"}).pretty()

//users 컬렉션의 "Casablanca" 영화를 좋아하는 사용자들에 대해서 좋아하는 영화 목록에 "The
// Maltese Falcon"을 중복 없이 추가하세요. 단, 해당 항목이 없는 경우는 무시하고, 여러 사람이 일치
// 한다면 모두 업데이트 하세요.
db.users.updateMany( {"favorites.movies": "Casablanca"},
    {$addToSet: {"favorites.movies": "The Maltese Falcon"} },
    {upsert: false})

db.users.find().pretty()

//numbers 컬렉션에 20,000개의 문서를 생성하세요.
for(let i = 0;  i < 20000; i++){
    db.numbers.insertOne({num:i})
}
//numbers 컬렉션의 문서 개수를 출력하세요.
db.numbers.count()

//numbers 컬렉션에서 num의 값이 20이상 25이하인 문서를 출력하세요.
db.numbers.find({
    num: { $gte: 20, $lte: 25 }
});

// 위 쿼리의 실행 통계를 출력하세요.
db.numbers.find({
    num: { $gte: 20, $lte: 25 }
}).explain("executionStats");

//numbers 컬렉션의 num 키에 대해서 인덱스를 생성하세요.
db.numbers.createIndex({ num: 1 });

// 위에서 생성한 인덱스 정보를 출력하세요.
db.numbers.getIndexes();

// 앞에서 작성한 범위 연산 쿼리의 실행 통계를 출력하고, 인덱스가 없을 때와 비교해보세요.
db.numbers.find({
    num: { $gte: 20, $lte: 25 }
}).explain("executionStats");