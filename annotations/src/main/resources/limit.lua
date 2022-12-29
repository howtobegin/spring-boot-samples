-- local key1 = KEY[1]
local key1 = KEYS[1]

local times = ARGV[1]

local expire = ARGV[2]

local val = redis.call('incr', key1)
-- ttl 获取key的生命时间，time to live
local ttl = redis.call('ttl', key1)
-- 如果是第一次对key值进行增加，设置有效期
if val == 1 then
	redis.call('expire', key1, expire)
else
	-- 如果key失效，重设过期时间
	if ttl == -1 then
		redis.call('expire',key1,tonumber(expire))
	end
end

if val > tonumber(times) then
	return 0
end

return 2