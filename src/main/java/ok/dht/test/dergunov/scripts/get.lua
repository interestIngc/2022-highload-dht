id = 0

request = function()
    path = "/v0/entity?id=key" .. id
    wrk.method = "GET"
    wrk.body = "value" .. id
    id = id + 1
    return wrk.format(nil, path)
end