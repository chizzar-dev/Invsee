<div align="center">

<img src="https://capsule-render.vercel.app/api?type=rect&color=0:0b1220,100:0e7490&height=110&section=header&text=InvSee&fontSize=42&fontColor=22d3ee&fontAlignY=54&desc=View%20and%20edit%20player%20inventories&descSize=13&descColor=94a3b8&descAlignY=80" width="100%" alt="InvSee" />

<p>
<img src="https://img.shields.io/github/v/release/chizzar-dev/Invsee?style=flat&label=release&color=06b6d4&labelColor=0b1220" alt="release" />
<img src="https://img.shields.io/badge/Minecraft-1.8%20%E2%80%93%201.21.11-0891b2?style=flat&labelColor=0b1220" alt="Minecraft 1.8 - 1.21.11" />
<img src="https://img.shields.io/badge/Java-8%2B-155e75?style=flat&labelColor=0b1220&logo=openjdk&logoColor=22d3ee" alt="Java 8+" />
<a href="LICENSE"><img src="https://img.shields.io/github/license/chizzar-dev/Invsee?style=flat&label=license&color=0e7490&labelColor=0b1220" alt="license" /></a>
</p>

</div>

InvSee lets staff open another player's inventory, including armour and offhand, or their ender chest, and edit it live.

*InvSee, yetkililerin baska bir oyuncunun envanterini (zirh ve yan el dahil) ya da ender sandigini acip canli duzenlemesini saglar.*

## Features · Özellikler
- `/invsee <oyuncu>` → 54 slotluk arayüzde envanter + **kask/göğüs/bacak/bot + yan el**
- `/invsee enderchest <oyuncu>` → ender sandığını **canlı** görüntüle ve düzenle
- Düzenlenebilir: item alıp verilebilir, değişiklikler hedefe uygulanır
- `allow-edit: false` ile salt-görüntüleme modu
- Sadece yetkililer (op / `invsee.use`)
- Bukkit API ile sürüm-güvenli; yan el 1.9+ gösterilir, 1.8'de otomatik gizlenir

## Installation · Kurulum
1. `InvSee.jar` dosyasını [Releases](https://github.com/chizzar-dev/Invsee/releases/latest) sayfasından indir.
2. Sunucunun `plugins/` klasörüne at.
3. Sunucuyu yeniden başlat.
4. `plugins/InvSee/config.yml` dosyasından ayarları düzenle.

## Commands · Komutlar
| Komut | Açıklama | Yetki |
|-------|----------|-------|
| `/invsee <oyuncu>` | Envanteri açar (zırh + yan el) | `invsee.use` |
| `/invsee enderchest <oyuncu>` | Ender sandığını açar (canlı) | `invsee.use` |

**Alias:** `/envanter` · `/inv` — ayrıca `enderchest` yerine `ender` veya `ec` yazılabilir.

## Permissions · Yetkiler
| Yetki | Açıklama | Varsayılan |
|-------|----------|------------|
| `invsee.use` | Başkalarının envanterini görür ve düzenler | op |

## Configuration · Ayarlar
| Anahtar | Açıklama |
|---------|----------|
| `title` | Arayüz başlığı (`%player%`) |
| `allow-edit` | Düzenlemeye izin ver / salt görüntüle |
| `filler.*` | Boş alanların dolgu itemi |
| `info.*` | Sağ alttaki bilgi itemi |
| `messages.*` | Tüm mesajlar |

## Notes · Notlar
- Şu an **çevrimiçi** oyuncular desteklenir. Hedef sen görüntülerken çıkarsa envanter
  değişiklikleri kaydedilmez ve uyarı verilir (ender sandığı canlı olduğu için etkilenmez).
- Zırh ve yan el slotları arayüzün alt sırasındadır; boşsa boş görünür.

## Building · Derleme
```bash
mvn clean package
```
Çıktı · Output: `target/InvSee.jar`

Her push [GitHub Actions](https://github.com/chizzar-dev/Invsee/actions/workflows/build.yml) ile derlenir; `v*` etiketli sürümler jar'la birlikte [Releases](https://github.com/chizzar-dev/Invsee/releases) sayfasına eklenir.
<br><sub>Every push is built by GitHub Actions; tagged `v*` releases attach the jar.</sub>

## License · Lisans
[MIT](LICENSE) — istediğin gibi kullan, değiştir, dağıt · use, modify and distribute freely

<div align="center"><sub>chizzar-dev · Minecraft plugins for 1.8 – 1.21.11</sub></div>
