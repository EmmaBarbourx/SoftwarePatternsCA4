function api(url, opts = {}) {
  const base = { headers: { 'Content-Type': 'application/json', ...(opts.headers||{}) } };
  return fetch(url, { ...base, ...opts })
        .then(r => r.ok ? r.json() : r.text().then(t => { throw new Error(t); }));
}

function qs(sel)  { return document.querySelector(sel); }
function byId(id) { return document.getElementById(id); }

// automatically add admin headers if stored
function withAuth(opts = {}) {
  const u = sessionStorage.adminUser, p = sessionStorage.adminPwd;
  if (u && p)
    return { ...opts, headers:{ ...(opts.headers||{}), 'X-Username':u, 'X-Password':p } };
  return opts;
}

function togglePwd(btn, id){
  const field = document.getElementById(id);
  const show  = field.type === 'password';
  field.type  = show ? 'text' : 'password';
  btn.textContent = show ? 'Hide' : 'Show';
}